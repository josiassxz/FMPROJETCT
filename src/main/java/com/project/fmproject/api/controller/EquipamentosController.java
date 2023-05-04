package com.project.fmproject.api.controller;


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

    @PostMapping
    public Equipamentos save(@RequestBody Equipamentos equipamentos) {
        return service.save(equipamentos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamentos> update(@PathVariable Long id, @RequestBody Equipamentos equipamentos) {
        if (!service.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        equipamentos.setId(id);
        return ResponseEntity.ok(service.save(equipamentos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Equipamentos> delete(@PathVariable Long id) {
        Optional<Equipamentos> equipamentos = service.findById(id);
        if (!equipamentos.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

