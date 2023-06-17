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
    private String tagEquipamento;
    private String descricao;
    private String localizacao;
    private String categoria;
    private String condicao;
    private String tipoEquipamento;
    private String inspecaoExterna;
    private String inspecaoInterna;
    private String proximaInspecaoExterna;
    private String proximaInspecaoInterna;
    private String placaIndentificacao;
    private String valvulaSeguranca;
    private String indicadorPressao;
    private String pmta;
    private String anoCadastro;
    private String observacao;
    private String numLacre;
    private String dataCalibracao;
    private String proximaCalibracao;
    private String roscaConexao;
    private String marcaModelo;
    private String instrumento;
    private String escala;
    private String unidadeMedida;
    private String tamanho;
    private String bitola;
    private String setPoint;
    private String unidadeAjuste;
    private String status;
    private String possuiRgi;
    private String fluido;
    private String numRelatorio;
    private String laudoValSeguranca;
    private String numSerie;
    private String capacidade;
    private String norma;


    @ManyToOne
    @JsonIgnoreProperties("empresa")
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @JsonIgnoreProperties("equipamento")
    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documentos> documentos = new ArrayList<>();

    @ElementCollection
    private List<String> caminhosDocumentos = new ArrayList<>();

    public void adicionarDocumento(Documentos documento, String caminho) {
        this.documentos.add(documento);
        this.caminhosDocumentos.add(caminho);
        documento.setEquipamento(this);
    }

    public void removeDocumento(Documentos documento) {
        this.documentos.remove(documento);
        this.caminhosDocumentos.remove(documento.getCaminho());
        documento.setEquipamento(null);
    }
}



