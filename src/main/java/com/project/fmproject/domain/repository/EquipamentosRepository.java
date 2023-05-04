package com.project.fmproject.domain.repository;

import com.project.fmproject.domain.model.Equipamentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentosRepository extends JpaRepository<Equipamentos, Long> {
    Equipamentos getById(Long id);
}
