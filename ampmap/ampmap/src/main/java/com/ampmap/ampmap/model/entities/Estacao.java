package com.ampmap.ampmap.model.entities;

import com.ampmap.ampmap.enumn.EstacaoStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "estacoes")
@Getter
@Setter
@NoArgsConstructor
public class Estacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "O nome da estação é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "A descrição da estação é obrigatória.")
    @Column(columnDefinition = "TEXT")
    private String descricao;

    @NotBlank(message = "O tipo de conector é obrigatório.")
    @Column(nullable = false)
    private String conector;

    // Ele não foi listado como obrigatório nas novas regras de *cadastro*,
    // mas pode ser importante para filtros ou outras funcionalidades.
    // Se for opcional no cadastro, não precisa de @NotNull aqui, mas pode ter no DTO de busca.
    private Double potencia; // Ex: 11.5 (para kW)


    @Enumerated(EnumType.STRING)

    @NotNull(message = "O valor por hora é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor por hora deve ser positivo.")
    @Digits(integer = 6, fraction = 2, message = "O valor por hora deve ter no máximo 2 casas decimais.")
    @Column(nullable = false, precision = 8, scale = 2) // Define precisão e escala no banco
    private BigDecimal valorPorHora;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status da estação é obrigatório.")

    private EstacaoStatus status;

    // Para as fotos, uma abordagem comum é armazenar as URLs das imagens.
    // O upload real seria tratado no serviço, salvando os arquivos em algum lugar (disco, S3, etc.)
    // e guardando os caminhos/URLs aqui.
    @ElementCollection // Se as URLs forem simples Strings e parte da entidade Estacao
    @CollectionTable(name = "estacao_fotos", joinColumns = @JoinColumn(name = "estacao_id"))
    @Column(name = "foto_url")
    private List<String> fotosUrls = new ArrayList<>();

    // Relacionamento com Usuário (Dono do Posto) - a ser implementado
    // @ManyToOne
    // @JoinColumn(name = "usuario_id", nullable = false)
    // private Usuario dono;

    @OneToMany(mappedBy = "Estacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();

}