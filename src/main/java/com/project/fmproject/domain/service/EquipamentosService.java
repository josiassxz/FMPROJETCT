package com.project.fmproject.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.DocumentosRepository;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
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

//    public Equipamentos salvarEquipamentosComDocumentos(Equipamentos equipamentos, Documentos documentos) {
//        documentos.setEquipamento(equipamentos);
//        equipamentos.getDocumentos().add(documentos);
//        equipamentosRepository.save(equipamentos);
//        return equipamentos;
//    }


    public Equipamentos salvar(String equipamentosJson, MultipartFile[] files) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Equipamentos equipamentos = mapper.readValue(equipamentosJson, Equipamentos.class);
        for (MultipartFile file : files) {
            Documentos documento = new Documentos();
            documento.setArquivo(file);
            equipamentos.getDocumentos().add(documento);
        }

        return equipamentosRepository.save(equipamentos);
    }

    public Equipamentos atualizar(Long id, Equipamentos equipamentos, MultipartFile[] files) throws IOException {
        Equipamentos equipamentoExistente = equipamentosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipamento não encontrado"));

        // Atualiza as propriedades do equipamento existente com as propriedades do equipamento recebido na requisição
        equipamentoExistente.setTag(equipamentos.getTag());
        equipamentoExistente.setDescricao(equipamentos.getDescricao());
        equipamentoExistente.setLocalizacao(equipamentos.getLocalizacao());
        equipamentoExistente.setCategoria(equipamentos.getCategoria());
        equipamentoExistente.setCondicao(equipamentos.getCondicao());
        equipamentoExistente.setCategoriaEquipamento(equipamentos.getCategoriaEquipamento());
        equipamentoExistente.setInspecaoExterna(equipamentos.getInspecaoExterna());
        equipamentoExistente.setInspecaoInterna(equipamentos.getInspecaoInterna());
        equipamentoExistente.setProximaInspecao(equipamentos.getProximaInspecao());
        equipamentoExistente.setTipoInspecao(equipamentos.getTipoInspecao());

        // Cria uma nova entidade Documentos para cada arquivo recebido e adiciona à lista de documentos do equipamento existente
        if (files != null) {
            for (MultipartFile file : files) {
                Documentos documento = new Documentos();
                documento.setArquivo(file);
                equipamentoExistente.getDocumentos().add(documento);
            }
        }

        // Salva o equipamento atualizado no banco de dados
        return equipamentosRepository.save(equipamentoExistente);
    }



    public void removerEquipamento(Long id) {
        equipamentosRepository.deleteById(id);
    }


}



