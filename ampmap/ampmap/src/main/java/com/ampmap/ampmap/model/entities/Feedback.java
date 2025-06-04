package com.ampmap.ampmap.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.UUID;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String comentario;

    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "estacao_id")
    private Estacao estacao;
}
