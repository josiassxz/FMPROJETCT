package com.project.fmproject.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotBlank(message = "O nome da empresa é obrigatório")
    private String nome;

//    @NotBlank(message = "A razão social é obrigatória")
    private String razaoSocial;


    private String cnpj;

    //@NotBlank(message = "O email é obrigatório")
    private String email;

    //@NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    private String cidade;

    private String cep;

    private String endereco;
    
    private String estado;

    @JsonIgnoreProperties("empresas")
    @ManyToMany
    @JoinTable(name = "empresa_usuario",
            joinColumns = @JoinColumn(name = "empresa_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    private List<Usuario> usuarios;

    @Transient
    private List<Long> idsUsuarios;

}
