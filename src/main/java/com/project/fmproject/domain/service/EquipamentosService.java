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


//    public Equipamentos salvar(String equipamentosJson, List<MultipartFile> files) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        Equipamentos equipamentos = mapper.readValue(equipamentosJson, Equipamentos.class);
//        for (MultipartFile file : files) {
//            String caminho = "C:\\Users\\sxz\\Desktop\\Arquivos\\" + UUID.randomUUID().getLeastSignificantBits() + file.getOriginalFilename();
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(caminho);
//            Files.write(path, bytes);
//            Documentos documento = new Documentos();
//            documento.setCaminho(caminho);
////
//            equipamentos.adicionarDocumento(documento, caminho);
//        }
//        return equipamentosRepository.save(equipamentos);
//    }



    public Equipamentos salvar(String equipamentosJson, List<MultipartFile> files) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Equipamentos equipamentos = mapper.readValue(equipamentosJson, Equipamentos.class);
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String caminho = "C:\\Users\\josia\\OneDrive\\Área de Trabalho\\Arquivos" + UUID.randomUUID().getLeastSignificantBits() + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(caminho);
            Files.write(path, bytes);
            Documentos documento = new Documentos();
            documento.setCaminho(caminho);
            documento.setNome(equipamentos.getDocumentos().get(i).getNome());
            documento.setTipo(equipamentos.getDocumentos().get(i).getTipo());
            equipamentos.adicionarDocumento(documento, caminho);
        }
        return equipamentosRepository.save(equipamentos);
    }


    public Equipamentos alterar(Long equipamentoId, String equipamentoJson, List<MultipartFile> files) throws IOException {
        Equipamentos equipamentoExistente = equipamentosRepository.findById(equipamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        ObjectMapper mapper = new ObjectMapper();
        Equipamentos equipamentoAtualizado = mapper.readValue(equipamentoJson, Equipamentos.class);
        equipamentoExistente.setTagEquipamento(equipamentoAtualizado.getTagEquipamento());
        equipamentoExistente.setDescricao(equipamentoAtualizado.getDescricao());
        equipamentoExistente.setLocalizacao(equipamentoAtualizado.getLocalizacao());
        equipamentoExistente.setCategoria(equipamentoAtualizado.getCategoria());
        equipamentoExistente.setCondicao(equipamentoAtualizado.getCondicao());
        equipamentoExistente.setTipoEquipamento(equipamentoAtualizado.getTipoEquipamento());
        equipamentoExistente.setInspecaoExterna(equipamentoAtualizado.getInspecaoExterna());
        equipamentoExistente.setInspecaoInterna(equipamentoAtualizado.getInspecaoInterna());
        equipamentoExistente.setProximaInspecaoExterna(equipamentoAtualizado.getProximaInspecaoExterna());
        equipamentoExistente.setProximaInspecaoInterna(equipamentoAtualizado.getProximaInspecaoInterna());
        equipamentoExistente.setPlacaIndentificacao(equipamentoAtualizado.getPlacaIndentificacao());
        equipamentoExistente.setValvulaSeguranca(equipamentoAtualizado.getValvulaSeguranca());
        equipamentoExistente.setIndicadorPressao(equipamentoAtualizado.getIndicadorPressao());
        equipamentoExistente.setPmta(equipamentoAtualizado.getPmta());
        equipamentoExistente.setAnoCadastro(equipamentoAtualizado.getAnoCadastro());
        equipamentoExistente.setObservacao(equipamentoAtualizado.getObservacao());
        equipamentoExistente.setNumLacre(equipamentoAtualizado.getNumLacre());
        equipamentoExistente.setDataCalibracao(equipamentoAtualizado.getDataCalibracao());
        equipamentoExistente.setProximaCalibracao(equipamentoAtualizado.getProximaCalibracao());
        equipamentoExistente.setRoscaConexao(equipamentoAtualizado.getRoscaConexao());
        equipamentoExistente.setMarcaModelo(equipamentoAtualizado.getMarcaModelo());
        equipamentoExistente.setInstrumento(equipamentoAtualizado.getInstrumento());
        equipamentoExistente.setEscala(equipamentoAtualizado.getEscala());
        equipamentoExistente.setUnidadeMedida(equipamentoAtualizado.getUnidadeMedida());
        equipamentoExistente.setTamanho(equipamentoAtualizado.getTamanho());
        equipamentoExistente.setBitola(equipamentoAtualizado.getBitola());
        equipamentoExistente.setSetPoint(equipamentoAtualizado.getSetPoint());
        equipamentoExistente.setUnidadeAjuste(equipamentoAtualizado.getUnidadeAjuste());
        equipamentoExistente.setStatus(equipamentoAtualizado.getStatus());
        equipamentoExistente.setPossuiRgi(equipamentoAtualizado.getPossuiRgi());
        equipamentoExistente.setFluido(equipamentoAtualizado.getFluido());
        equipamentoExistente.setNumRelatorio(equipamentoAtualizado.getNumRelatorio());
        equipamentoExistente.setLaudoValSeguranca(equipamentoAtualizado.getLaudoValSeguranca());
        equipamentoExistente.setNumSerie(equipamentoAtualizado.getNumSerie());
        equipamentoExistente.setCapacidade(equipamentoAtualizado.getCapacidade());
        equipamentoExistente.setNorma(equipamentoAtualizado.getNorma());
        // Remove os documentos antigos antes de adicionar os novos
        equipamentoExistente.getDocumentos().clear();
//        for (MultipartFile file : files) {
//            String caminho = "C:\\Users\\sxz\\Desktop\\Arquivos" + UUID.randomUUID().getLeastSignificantBits() + file.getOriginalFilename();
//            byte[] bytes = file.getBytes();
//            Path path = Paths.get(caminho);
//            Files.write(path, bytes);
//            Documentos documento = new Documentos();
//            documento.setCaminho(caminho);
//            equipamentoExistente.adicionarDocumento(documento, caminho);
//        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String caminho = "C:\\Users\\josia\\OneDrive\\Área de Trabalho\\Arquivos" + UUID.randomUUID().getLeastSignificantBits() + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(caminho);
            Files.write(path, bytes);
            Documentos documento = new Documentos();
            documento.setCaminho(caminho);
            documento.setNome(equipamentoAtualizado.getDocumentos().get(i).getNome());
            documento.setTipo(equipamentoAtualizado.getDocumentos().get(i).getTipo());
            equipamentoExistente.adicionarDocumento(documento, caminho);
        }

        return equipamentosRepository.save(equipamentoExistente);
    }



    public void removerEquipamento(Long id) {
        equipamentosRepository.deleteById(id);
    }


}



