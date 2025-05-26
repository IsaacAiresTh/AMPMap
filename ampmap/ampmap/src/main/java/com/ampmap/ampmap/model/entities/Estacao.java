package com.ampmap.ampmap.model.entities;

import com.ampmap.ampmap.enumn.EstacaoStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "estacoes")
@Data
public class Estacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String conector;

    private Double potencia;

    @Enumerated(EnumType.STRING)
    private EstacaoStatus status;

    //colocar atributos restantes
}
