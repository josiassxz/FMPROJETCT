package com.project.fmproject.domain.service;

import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.DocumentosRepository;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipamentosService {

    @Autowired
    private EquipamentosRepository repository;

    @Autowired
    private DocumentosRepository documentosRepository;

    @Autowired
    private EquipamentosRepository equipamentosRepository;

    public List<Equipamentos> findAll() {
        return repository.findAll();
    }

    public Optional<Equipamentos> findById(Long id) {
        return repository.findById(id);
    }

    public Equipamentos salvarEquipamentosComDocumentos(Equipamentos equipamentos, Documentos documentos) {
        equipamentos.getDocumentos().add(documentos);
        documentos.setEquipamento(equipamentos);
        equipamentosRepository.save(equipamentos);
        return equipamentos;
    }

//    public Equipamentos salvarEquipamento(Equipamentos equipamento) {
//        return equipamentosRepository.save(equipamento);
//    }

    public void removerEquipamento(Long id) {
        equipamentosRepository.deleteById(id);
    }


}



