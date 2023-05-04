package com.project.fmproject.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Equipamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    private String descricao;

    private String localizacao;

    private String categoria;

    private String condicao;

    private String categoriaEquipamento;

    private String inspecaoExterna;

    private String inspecaoInterna;

    private String proximaInspecao;

    private String tipoInspecao;


    @ManyToOne
    @JsonIgnoreProperties("empresa")
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @JsonIgnoreProperties("equipamento")
    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documentos> documentos = new ArrayList<>();
}




