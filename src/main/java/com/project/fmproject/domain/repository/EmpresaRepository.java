package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Empresa;
import com.project.fmproject.domain.model.Equipamentos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {

    List<Empresa> findByNomeContainingIgnoreCase(String nome);


    Optional<Empresa> findByCnpj(String cnpj);


}
