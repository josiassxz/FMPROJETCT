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
import java.io.File;
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
        Equipamentos equipamento = mapper.readValue(equipamentosJson, Equipamentos.class);

        // Configurar a relação bidirecional entre Equipamento e Documento
        List<Documentos> documentos = new ArrayList<>();
        new File("C:\\arquivos").mkdirs();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            String caminho = "C:\\arquivos\\" + UUID.randomUUID().getLeastSignificantBits() + " - " + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(caminho);
            Files.write(path, bytes);
            Documentos documento = new Documentos();
            documento.setCaminho(caminho);
            documento.setNome(equipamento.getDocumentos().get(i).getNome());
            documento.setTipo(equipamento.getDocumentos().get(i).getTipo());
            documento.setEquipamento(equipamento); // Estabelecer a relação com o equipamento

            documentos.add(documento);
        }

        equipamento.setDocumentos(documentos);

        return equipamentosRepository.save(equipamento);
    }





    public Equipamentos alterar(Long equipamentoId, String equipamentosJson, List<MultipartFile> files) throws IOException {
        Equipamentos equipamentoExistente = equipamentosRepository.findById(equipamentoId)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado"));

        ObjectMapper mapper = new ObjectMapper();
        Equipamentos equipamentoAtualizado = mapper.readValue(equipamentosJson, Equipamentos.class);
        equipamentoExistente.setTagEquipamento(equipamentoAtualizado.getTagEquipamento());
        equipamentoExistente.setDescricao(equipamentoAtualizado.getDescricao());
        equipamentoExistente.setTagEquipamento(equipamentoAtualizado.getTagEquipamento());
        equipamentoExistente.setDescricao(equipamentoAtualizado.getDescricao());
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
        equipamentoExistente.setInspecao(equipamentoAtualizado.getInspecao());
        equipamentoExistente.setProximaInspecao(equipamentoAtualizado.getProximaInspecao());
        equipamentoExistente.setObservacaoRgi(equipamentoAtualizado.getObservacaoRgi());

        if (equipamentoAtualizado.getDocumentos() != null && !equipamentoAtualizado.getDocumentos().isEmpty()) {
            int numDocumentos = Math.min(equipamentoAtualizado.getDocumentos().size(), files.size());

            Iterator<Documentos> documentoIterator = equipamentoExistente.getDocumentos().iterator();
            while (documentoIterator.hasNext()) {
                Documentos documentoExistente = documentoIterator.next();

                boolean documentoEncontrado = false;
                for (int i = 0; i < numDocumentos; i++) {
                    Documentos documentoAtualizado = equipamentoAtualizado.getDocumentos().get(i);
                    if (documentoExistente.getId() != null && documentoExistente.getId().equals(documentoAtualizado.getId())) {
                        documentoEncontrado = true;

                        if (!documentoExistente.getTipo().equals(documentoAtualizado.getTipo())) {
                            documentoExistente.setTipo(documentoAtualizado.getTipo());
                        }

                        equipamentoAtualizado.getDocumentos().remove(i);
                        numDocumentos--;
                        break;
                    }
                }

                if (!documentoEncontrado) {
                    documentoIterator.remove();
                }
            }

            for (int i = 0; i < numDocumentos; i++) {
                MultipartFile file = files.get(i);
                Documentos documentoAtualizado = equipamentoAtualizado.getDocumentos().get(i);

                if (!file.isEmpty()) {
                    String caminho = "C:\\arquivos\\" + UUID.randomUUID().getLeastSignificantBits() + " - " + file.getOriginalFilename();
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(caminho);
                    Files.write(path, bytes);
                    Documentos documento = new Documentos();
                    documento.setCaminho(caminho);
                    documento.setNome(documentoAtualizado.getNome());
                    documento.setTipo(documentoAtualizado.getTipo());
                    equipamentoExistente.adicionarDocumento(documento, caminho);
                } else {
                    equipamentoExistente.adicionarDocumento(documentoAtualizado, documentoAtualizado.getCaminho());
                }
            }
        } else {
            equipamentoExistente.getDocumentos().clear();
        }

        return equipamentosRepository.save(equipamentoExistente);

    }


    public void removerEquipamento(Long id) {
        equipamentosRepository.deleteById(id);
    }


}



