// Código completo do EstacaoService.java atualizado com Geocoding

package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.EstacaoCriacaoDTO;
import com.ampmap.ampmap.dtos.EstacaoDTO;
import com.ampmap.ampmap.enumn.EstacaoStatus;
import com.ampmap.ampmap.model.entities.Estacao;
import com.ampmap.ampmap.model.entities.Usuario;
import com.ampmap.ampmap.model.mappers.EstacaoMapper;
import com.ampmap.ampmap.repositories.EstacaoRepository;
import com.ampmap.ampmap.repositories.UsuarioRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map; // <-- IMPORTAR MAP
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final GeocodingService geocodingService; // <-- INJETAR O NOVO SERVIÇO
    private final Path rootLocation = Paths.get("upload-dir");


    // <-- ATUALIZAR CONSTRUTOR PARA INCLUIR O GeocodingService
    public EstacaoService(EstacaoRepository estacaoRepository, UsuarioRepository usuarioRepository, GeocodingService geocodingService) {
        this.estacaoRepository = estacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.geocodingService = geocodingService; // <-- ATRIBUIR O SERVIÇO
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível inicializar o armazenamento de arquivos!", e);
        }
    }

    @Transactional
    public EstacaoDTO criarEstacao(EstacaoCriacaoDTO dto, MultipartFile[] fotos) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario dono = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // <-- CHAMAR O SERVIÇO DE GEOCODIFICAÇÃO
        Map<String, Double> coordinates = geocodingService.getCoordinatesFromAddress(
            dto.logradouro(),
            dto.numero(),
            dto.cidade(),
            dto.estado()
        );

        Estacao estacao = new Estacao();
        estacao.setNome(dto.nome());
        estacao.setDescricao(dto.descricao());
        estacao.setConector(dto.conector());
        estacao.setPotencia(dto.potencia());
        estacao.setValorPorHora(dto.valorPorHora());
        estacao.setDono(dono);

        // <-- SALVAR LATITUDE E LONGITUDE VINDAS DO SERVIÇO DE GEOCODIFICAÇÃO
        estacao.setLatitude(coordinates.get("latitude"));
        estacao.setLongitude(coordinates.get("longitude"));

        if (dto.status() != null) {
            estacao.setStatus(dto.status());
        } else {
            estacao.setStatus(EstacaoStatus.Inativo);
        }

        List<String> urlsSalvas = new ArrayList<>();
        if (fotos != null && fotos.length > 0) {
            for (MultipartFile foto : fotos) {
                if (!foto.isEmpty()) {
                    String filename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                    try {
                        Files.copy(foto.getInputStream(), this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                        urlsSalvas.add("/uploads/" + filename);
                    } catch (IOException e) {
                        throw new RuntimeException("Falha ao salvar a foto: " + filename, e);
                    }
                }
            }
        }
        estacao.setFotosUrls(urlsSalvas);

        Estacao novaEstacao = estacaoRepository.save(estacao);
        return EstacaoMapper.convertToDto(novaEstacao);
    }

    public List<EstacaoDTO> obterEstacaoPorFiltros(String conector, Double potencia, EstacaoStatus status) {
        Estacao estacaoFiltro = new Estacao();
        estacaoFiltro.setConector(conector);
        estacaoFiltro.setPotencia(potencia);
        estacaoFiltro.setStatus(status);

        ExampleMatcher matcher = ExampleMatcher
                .matchingAll()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreNullValues();

        Example<Estacao> example = Example.of(estacaoFiltro, matcher);
        List<Estacao> estacoes = estacaoRepository.findAll(example);
        return estacoes.stream()
                       .map(EstacaoMapper::convertToDto)
                       .collect(Collectors.toList());
    }

    public EstacaoDTO obterEstacaoPorId(UUID id) {
        Estacao estacao = estacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estação não encontrada com o ID: " + id));
        return EstacaoMapper.convertToDto(estacao);
    }
    
    @Transactional
    public EstacaoDTO atualizarEstacao(UUID id, EstacaoCriacaoDTO dto) {
        Estacao estacao = estacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estação não encontrada com o ID: " + id));

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!estacao.getDono().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Acesso negado. Você não é o proprietário desta estação.");
        }

        // <-- LÓGICA DE ATUALIZAÇÃO DE ENDEREÇO E COORDENADAS
        Map<String, Double> coordinates = geocodingService.getCoordinatesFromAddress(
            dto.logradouro(),
            dto.numero(),
            dto.cidade(),
            dto.estado()
        );
        estacao.setLatitude(coordinates.get("latitude"));
        estacao.setLongitude(coordinates.get("longitude"));

        estacao.setNome(dto.nome());
        estacao.setDescricao(dto.descricao());
        estacao.setConector(dto.conector());
        estacao.setPotencia(dto.potencia());
        estacao.setValorPorHora(dto.valorPorHora());
        if (dto.status() != null) {
            estacao.setStatus(dto.status());
        }

        Estacao estacaoAtualizada = estacaoRepository.save(estacao);
        return EstacaoMapper.convertToDto(estacaoAtualizada);
    }

    @Transactional
    public void deletarEstacao(UUID id) {
        Estacao estacao = estacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estação não encontrada com o ID: " + id));
        
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!estacao.getDono().getEmail().equals(userEmail)) {
            throw new AccessDeniedException("Acesso negado. Você não é o proprietário desta estação.");
        }

        estacaoRepository.delete(estacao);
    }
}