package com.ampmap.ampmap.model.entities;

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

    private String potencia;

    private String status;

    //colocar atributos restantes
}
