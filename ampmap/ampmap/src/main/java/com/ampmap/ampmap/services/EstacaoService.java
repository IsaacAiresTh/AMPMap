package com.ampmap.ampmap.services;

import com.ampmap.ampmap.dtos.EstacaoDTO; // Não é usado diretamente aqui, mas o controller usará
import com.ampmap.ampmap.model.entities.Estacao;
import com.ampmap.ampmap.repositories.EstacaoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;

    public EstacaoService(EstacaoRepository estacaoRepository) {
        this.estacaoRepository = estacaoRepository;
    }

    //metodo para obter as estacoes por filtros, usando o Example
    public List<Estacao> obterEstacaoPorFiltros(String conector, Double potencia, String status) {
        Estacao estacao = new Estacao();
        estacao.setConector(conector);
        estacao.setPotencia(potencia);
        estacao.setStatus(status);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase() // Apenas uma vez é necessário
                // .withStringMatcher(ExampleMatcher.StringMatcher.STARTING); // Avalie se STARTING é o ideal
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // Ou CONTAINING para mais flexibilidade

        Example<Estacao> example = Example.of(estacao, matcher);

        return estacaoRepository.findAll(example);
    }
}
    