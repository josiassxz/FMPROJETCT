package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Equipamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long>, JpaSpecificationExecutor<Equipamentos> {
    Equipamentos getById(Long id);

    List<Equipamentos> findAllByEmpresa_Id(Long id);
}
