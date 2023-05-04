package com.project.fmproject.api.controller;


import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.service.EquipamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/equipamentos")
public class EquipamentosController {

    @Autowired
    private EquipamentosService service;

    @GetMapping
    public List<Equipamentos> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamentos> findById(@PathVariable Long id) {
        Optional<Equipamentos> equipamentos = service.findById(id);
        return equipamentos.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/salvar")
    public Equipamentos salvarEquipamentosComDocumentos(@RequestBody Equipamentos equipamentos, Documentos documentos) {
        return service.salvarEquipamentosComDocumentos(equipamentos, documentos);
    }

    @PutMapping("/{id}")
    public Equipamentos atualizarEquipamento(@PathVariable Long id, @RequestBody Equipamentos equipamentos, Documentos documentos) {
        equipamentos.setId(id);
        return service.salvarEquipamentosComDocumentos(equipamentos, documentos);
    }


    @DeleteMapping("/{id}")
    public void removerEquipamento(@PathVariable Long id) {
        service.removerEquipamento(id);
    }

}

