package com.project.fmproject.api.controller;


import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.service.EquipamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
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


    @PostMapping(value = "/salvar")
    public ResponseEntity<Equipamentos> salvar(@RequestParam("equipamento") String equipamentos,
                                               @RequestPart("files") List<MultipartFile> files) throws IOException {
        Equipamentos novoEquipamento = service.salvar(equipamentos, files.toArray(new MultipartFile[files.size()]));
        return ResponseEntity.created(URI.create("/equipamentos/" + novoEquipamento.getId())).body(novoEquipamento);
    }




    @PutMapping("/{id}")
    public ResponseEntity<Equipamentos> atualizar(@PathVariable Long id, @RequestBody Equipamentos equipamentos, @RequestParam(name = "files", required = false) MultipartFile[] files) throws IOException {
        Equipamentos equipamentoAtualizado = service.atualizar(id, equipamentos, files);
        return ResponseEntity.ok(equipamentoAtualizado);
    }


    @DeleteMapping("/{id}")
    public void removerEquipamento(@PathVariable Long id) {
        service.removerEquipamento(id);
    }

}

