package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.CarroCriacaoDTO;
import com.ampmap.ampmap.dtos.CarsDTO;
import com.ampmap.ampmap.model.entities.Cars;
import com.ampmap.ampmap.model.entities.Usuario;
import com.ampmap.ampmap.model.mappers.UsuarioMapper;
import com.ampmap.ampmap.repositories.CarroRepository;
import com.ampmap.ampmap.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarroService {

    private final CarroRepository carroRepository;
    private final UsuarioRepository usuarioRepository;

    public CarroService(CarroRepository carroRepository, UsuarioRepository usuarioRepository) {
        this.carroRepository = carroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private Usuario getAuthenticatedUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado!"));
    }

    @Transactional
    public CarsDTO adicionarCarro(CarroCriacaoDTO dto) {
        Usuario dono = getAuthenticatedUser();
        Cars novoCarro = new Cars();
        novoCarro.setMarca(dto.marca());
        novoCarro.setModelo(dto.modelo());
        novoCarro.setTipo_conector(dto.tipo_conector());
        novoCarro.setUsuario(dono);

        Cars carroSalvo = carroRepository.save(novoCarro);
        return new CarsDTO(carroSalvo.getMarca(), carroSalvo.getModelo(), UsuarioMapper.convertToDto(dono));
    }

    public List<CarsDTO> listarCarrosDoUsuarioLogado() {
        Usuario dono = getAuthenticatedUser();
        return carroRepository.findByUsuario(dono).stream()
                .map(carro -> new CarsDTO(carro.getMarca(), carro.getModelo(), null)) // Retorna DTO sem dados do dono para simplicidade
                .collect(Collectors.toList());
    }

    @Transactional
    public CarsDTO atualizarCarro(UUID carroId, CarroCriacaoDTO dto) {
        Usuario dono = getAuthenticatedUser();
        Cars carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado!"));

        if (!carro.getUsuario().getId().equals(dono.getId())) {
            throw new AccessDeniedException("Acesso negado. Você não pode atualizar um carro que não é seu.");
        }

        carro.setMarca(dto.marca());
        carro.setModelo(dto.modelo());
        carro.setTipo_conector(dto.tipo_conector());
        
        Cars carroAtualizado = carroRepository.save(carro);
        return new CarsDTO(carroAtualizado.getMarca(), carroAtualizado.getModelo(), UsuarioMapper.convertToDto(dono));
    }

    @Transactional
    public void deletarCarro(UUID carroId) {
        Usuario dono = getAuthenticatedUser();
        Cars carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado!"));

        if (!carro.getUsuario().getId().equals(dono.getId())) {
            throw new AccessDeniedException("Acesso negado. Você não pode deletar um carro que não é seu.");
        }

        carroRepository.delete(carro);
    }
}