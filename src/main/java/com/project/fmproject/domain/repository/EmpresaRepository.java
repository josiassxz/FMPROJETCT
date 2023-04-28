package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findByNomeContainingIgnoreCase(String nome);


    Optional<Empresa> findByCnpj(String cnpj);


}
