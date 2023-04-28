package com.project.fmproject.domain.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nome;
    private String tipo;

}