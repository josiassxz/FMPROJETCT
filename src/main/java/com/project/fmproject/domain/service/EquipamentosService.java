package com.project.fmproject.domain.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.DocumentosRepository;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class EquipamentosService {

    @Autowired
    private EquipamentosRepository repository;


    @Autowired
    private EquipamentosRepository equipamentosRepository;

    public Page<Equipamentos> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    public Optional<Equipamentos> findById(Long id) {
        return repository.findById(id);
    }


    public Equipamentos salvar(String equipamentosJson, List<MultipartFile> files) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Equipamentos equipamentos = mapper.readValue(equipamentosJson, Equipamentos.class);
        for (MultipartFile file : files) {
            String caminho = "C:\\Users\\josia\\OneDrive\\Área de Trabalho\\Arquivos\\" + UUID.randomUUID().getLeastSignificantBits() + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(caminho);
            Files.write(path, bytes);
            Documentos documento = new Documentos();
            documento.setCaminho(caminho);
            equipamentos.adicionarDocumento(documento, caminho);
        }
        return equipamentosRepository.save(equipamentos);
    }

    public Equipamentos alterar(Long id, String equipamentosJson, List<MultipartFile> files) throws IOException {
        Equipamentos equipamentos = equipamentosRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Equipamento não encontrado"));

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.updateValue(equipamentos, equipamentosJson);

        List<Documentos> documentosAntigos = new ArrayList<>(equipamentos.getDocumentos());

        for (MultipartFile file : files) {
            String caminho = "C:\\Users\\josia\\OneDrive\\Área de Trabalho\\Arquivos\\" + UUID.randomUUID().getLeastSignificantBits() + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(caminho);
            Files.write(path, bytes);
            Documentos documento = new Documentos();
            documento.setCaminho(caminho);
            equipamentos.adicionarDocumento(documento, caminho);
        }

        for (Documentos documento : documentosAntigos) {
            if (!equipamentos.getDocumentos().contains(documento)) {
                equipamentos.removeDocumento(documento);
            }
        }

        return equipamentosRepository.save(equipamentos);
    }


    public void removerEquipamento(Long id) {
        equipamentosRepository.deleteById(id);
    }


}



