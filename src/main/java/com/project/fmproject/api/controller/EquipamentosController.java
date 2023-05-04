package com.project.fmproject.api.controller;


import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.DocumentosRepository;
import com.project.fmproject.domain.service.EquipamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/equipamentos")
public class EquipamentosController {

    @Autowired
    private EquipamentosService service;

    @Autowired
    private DocumentosRepository documentosRepository;

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
    public ResponseEntity<Equipamentos> salvar(@RequestParam("equipamento") String equipamentosJson,
                                               @RequestPart("files") List<MultipartFile> files) throws IOException {
        Equipamentos novoEquipamento = service.salvar(equipamentosJson, files);
        return ResponseEntity.created(URI.create("/equipamentos/" + novoEquipamento.getId())).body(novoEquipamento);
    }


    @GetMapping("/download/{idDocumento}")
    public ResponseEntity<Resource> downloadDocumento(@PathVariable Long idDocumento) {
        Optional<Documentos> optionalDocumento = documentosRepository.findById(idDocumento);
        if (optionalDocumento.isPresent()) {
            Documentos documento = optionalDocumento.get();
            Path caminho = Paths.get(documento.getCaminho());
            Resource resource;
            try {
                resource = new UrlResource(caminho.toUri());
            } catch (MalformedURLException e) {
                throw new RuntimeException("Erro ao fazer o download do arquivo");
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
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

