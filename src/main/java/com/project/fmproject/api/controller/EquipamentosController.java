package com.project.fmproject.api.controller;


import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.DocumentosRepository;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import com.project.fmproject.domain.service.EquipamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;



@RestController
@CrossOrigin("http://localhost:4200")
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




/* RETORNA UM BASE64 NAO RECOMENDADO*/
//    @CrossOrigin(origins = "*")
//    @GetMapping("/download/{idDocumento}")
//    public ResponseEntity<String> downloadDocumento(@PathVariable Long idDocumento) {
//        Optional<Documentos> optionalDocumento = documentosRepository.findById(idDocumento);
//        if (optionalDocumento.isPresent()) {
//            Documentos documento = optionalDocumento.get();
//            Path caminho = Paths.get(documento.getCaminho());
//            try {
//                byte[] bytes = Files.readAllBytes(caminho);
//                String base64 = Base64.getEncoder().encodeToString(bytes);
//                HttpHeaders headers = new HttpHeaders();
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                headers.setContentDispositionFormData("attachment", caminho.getFileName().toString());
//                return new ResponseEntity<>(base64, headers, HttpStatus.OK);
//            } catch (IOException e) {
//                throw new RuntimeException("Erro ao fazer o download do arquivo");
//            }
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    /*RETORNAR UM BINARIO !! RECOMENDADO*/
    @GetMapping("/download/{idDocumento}")
    public ResponseEntity<byte[]> downloadDocumento(@PathVariable Long idDocumento) throws IOException {
        Optional<Documentos> optionalDocumento = documentosRepository.findById(idDocumento);
        if (optionalDocumento.isPresent()) {
            Documentos documento = optionalDocumento.get();
            Path caminho = Paths.get(documento.getCaminho());
            byte[] bytes = Files.readAllBytes(caminho);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + caminho.getFileName().toString() + "\"")
                    .body(bytes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Equipamentos> alterar(@PathVariable Long id, @RequestParam("equipamento") String equipamentosJson, @RequestParam("files") List<MultipartFile> files) {
        try {
            Equipamentos equipamentos = service.alterar(id, equipamentosJson, files);
            return ResponseEntity.ok(equipamentos);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public void removerEquipamento(@PathVariable Long id) {
        service.removerEquipamento(id);
    }

}

