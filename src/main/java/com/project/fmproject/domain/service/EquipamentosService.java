package com.project.fmproject.domain.service;

import com.project.fmproject.domain.exception.EntidadeNaoEncontradaException;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentosService {

    @Autowired
    private EquipamentosRepository repository;

    public List<Equipamentos> findAll() {
        return repository.findAll();
    }

    public Optional<Equipamentos> findById(Long id) {
        return repository.findById(id);
    }

    public Equipamentos save(Equipamentos equipamentos) {
        return repository.save(equipamentos);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}



