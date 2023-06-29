package com.project.fmproject.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.fmproject.domain.service.enums.TipoDocumentoEnum;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Data
public class Documentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String caminho;
    private String nome;
    private String tipo;
    @Lob
    private byte[] arquivo;

    @ManyToOne
    @JsonIgnoreProperties("documentos")
    @JoinColumn(name = "equipamento_id")
    private Equipamentos equipamento;

//    public void setArquivo(MultipartFile file) throws IOException {
//        this.nome = file.getOriginalFilename();
//        this.tipo = file.getContentType();
//        this.arquivo = file.getBytes();
//    }
}

