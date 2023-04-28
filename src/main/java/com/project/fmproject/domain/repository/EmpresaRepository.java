package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findByCnpj(String cnpj);

}
