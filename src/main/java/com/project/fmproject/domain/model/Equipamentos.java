package com.project.fmproject.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.fmproject.domain.service.enums.TipoEquipamentoEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Equipamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tagEquipamento;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private String localizacao;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String condicao;
    @Column(nullable = false)
    private TipoEquipamentoEnum tipoEquipamento;
    @Column(nullable = false)
    private String inspecaoExterna;
    @Column(nullable = false)
    private String inspecaoInterna;
    @Column(nullable = false)
    private String proximaInspecaoExterna;
    @Column(nullable = false)
    private String proximaInspecaoInterna;
    @Column(nullable = false)
    private String placaIndentificacao;
    @Column(nullable = false)
    private String valvulaSeguranca;
    @Column(nullable = false)
    private String indicadorPressao;
    @Column(nullable = false)
    private String pmta;
    @Column(nullable = false)
    private String anoCadastro;
    @Column(nullable = false)
    private String observacao;
    @Column(nullable = false)
    private String numLacre;
    @Column(nullable = false)
    private String dataCalibracao;
    @Column(nullable = false)
    private String proximaCalibracao;
    @Column(nullable = false)
    private String roscaConexao;
    @Column(nullable = false)
    private String marcaModelo;
    @Column(nullable = false)
    private String instrumento;
    @Column(nullable = false)
    private String escala;
    @Column(nullable = false)
    private String unidadeMedida;
    @Column(nullable = false)
    private String tamanho;
    @Column(nullable = false)
    private String bitola;
    @Column(nullable = false)
    private String setPoint;
    @Column(nullable = false)
    private String unidadeAjuste;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String possuiRgi;
    @Column(nullable = false)
    private String fluido;
    @Column(nullable = false)
    private String numRelatorio;
    @Column(nullable = false)
    private String laudoValSeguranca;
    @Column(nullable = false)
    private String numSerie;
    @Column(nullable = false)
    private String capacidade;
    @Column(nullable = false)
    private String norma;
    @Column(nullable = false)
    private String inspecao;
    @Column(nullable = false)
    private String proximaInspecao;
    @Column(nullable = false)
    private String observacaoRgi;
    

    @ManyToOne
    @JsonIgnoreProperties("empresa")
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @JsonIgnoreProperties("equipamento")
    @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documentos> documentos = new ArrayList<>();

    @JsonIgnore
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



