package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.EstacaoCriacaoDTO;
import com.ampmap.ampmap.enumn.EstacaoStatus;
import com.ampmap.ampmap.model.entities.Estacao;
import com.ampmap.ampmap.repositories.EstacaoRepository;
// import com.ampmap.ampmap.services.FileStorageService; // Um serviço hipotético para salvar arquivos
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;
    // Para o armazenamento de arquivos, você pode injetar um serviço específico.
    // Exemplo simplificado de armazenamento em diretório local:
    private final Path rootLocation = Paths.get("upload-dir"); // Configure no application.properties

    // @Autowired
    // private FileStorageService fileStorageService; // Exemplo de serviço dedicado

    public EstacaoService(EstacaoRepository estacaoRepository) {
        this.estacaoRepository = estacaoRepository;
        // Criar o diretório de upload se não existir (para o exemplo simplificado)
        try {
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível inicializar o armazenamento de arquivos!", e);
        }
    }

    @Transactional // Garante que ou tudo é salvo (entidade + referências de fotos) ou nada
    public Estacao criarEstacao(EstacaoCriacaoDTO dto, MultipartFile[] fotos) {
        Estacao estacao = new Estacao();
        estacao.setNome(dto.nome());
        estacao.setDescricao(dto.descricao());
        estacao.setConector(dto.conector());
        estacao.setPotencia(dto.potencia());
        estacao.setValorPorHora(dto.valorPorHora());

        // Definir um status padrão se não vier no DTO, ou usar o do DTO
        if (dto.status() != null) {
            estacao.setStatus(dto.status());
        } else {
            estacao.setStatus(EstacaoStatus.Inativo);
        }

        List<String> urlsSalvas = new ArrayList<>();
        if (fotos != null && fotos.length > 0) {
            for (MultipartFile foto : fotos) {
                if (!foto.isEmpty()) {
                    // Lógica para salvar o arquivo (exemplo simplificado)
                    // Em um cenário real, use um serviço dedicado e robusto para armazenamento (ex: S3, Azure Blob)
                    // e gere URLs acessíveis publicamente ou protegidas.
                    String filename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                    try {
                        Files.copy(foto.getInputStream(), this.rootLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
                        // A URL aqui seria relativa ao seu sistema de arquivos ou a URL pública se em nuvem
                        urlsSalvas.add("/uploads/" + filename); // Exemplo de URL (ajuste conforme sua config de servir estáticos)
                    } catch (IOException e) {
                        // Tratar exceção de falha no upload (rollback, log, etc.)
                        throw new RuntimeException("Falha ao salvar a foto: " + filename, e);
                    }
                }
            }
        }
        estacao.setFotosUrls(urlsSalvas);

        // Adicionar lógica para associar ao "Dono do Posto" (Usuário) quando implementado
        // User dono = userRepository.findById(idDoDonoLogado).orElseThrow(...);
        // estacao.setDono(dono);

        return estacaoRepository.save(estacao);
    }

    public List<Estacao> obterEstacaoPorFiltros(String conector, Double potencia, EstacaoStatus status) {
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
        return estacaoRepository.findAll(example);
    }
}