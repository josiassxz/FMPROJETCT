package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Empresa;
import com.project.fmproject.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Usuario findByEmailAndSenha(String email, String senha);

    Usuario findByEmailContaining(String email);

    Usuario findByNomeContaining(String nome);





}
