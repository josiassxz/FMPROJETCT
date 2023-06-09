package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentosRepository extends JpaRepository<Documentos, Long> {

}
