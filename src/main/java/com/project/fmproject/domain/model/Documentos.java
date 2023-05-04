package com.project.fmproject.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Documentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tipo;

    private String caminhoArquivo;

    @ManyToOne
    @JsonIgnoreProperties("documentos")
    @JoinColumn(name = "equipamento_id")
    private Equipamentos equipamento;

    // getters e setters
}