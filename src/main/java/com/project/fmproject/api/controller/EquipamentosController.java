package com.project.fmproject.api.controller;


import com.project.fmproject.domain.model.Documentos;
import com.project.fmproject.domain.model.Equipamentos;
import com.project.fmproject.domain.repository.DocumentosRepository;
import com.project.fmproject.domain.repository.EquipamentosRepository;
import com.project.fmproject.domain.service.EquipamentosService;
import com.project.fmproject.domain.service.enums.TipoEquipamentoEnum;
import com.project.fmproject.domain.specification.EquipamentosSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.http.ContentDisposition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;




@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/equipamentos")
public class EquipamentosController {


    @Autowired
    private EquipamentosService service;

    @Autowired
    private DocumentosRepository documentosRepository;

    private final EquipamentosRepository equipamentosRepository;

    @Autowired
    public EquipamentosController(EquipamentosRepository equipamentosRepository) {
        this.equipamentosRepository = equipamentosRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Equipamentos>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Equipamentos> equipamentosPage = service.findAll(pageable);
        return ResponseEntity.ok(equipamentosPage);
    }

    @GetMapping("/lista/{id}")
    public ResponseEntity<List<Equipamentos>> findAll(@RequestParam Long id) {
        return ResponseEntity.ok(equipamentosRepository.findAllByEmpresa_Id(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipamentos> findById(@PathVariable Long id) {
        Optional<Equipamentos> equipamentos = service.findById(id);
        return equipamentos.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }





//    @PostMapping(value = "/salvar")
//    public ResponseEntity<Equipamentos> salvar(@RequestParam("equipamento") String equipamentosJson,
//                                               @RequestPart("files") List<MultipartFile> files) throws IOException {
//        Equipamentos novoEquipamento = service.salvar(equipamentosJson, files);
//        return ResponseEntity.created(URI.create("/equipamentos/" + novoEquipamento.getId())).body(novoEquipamento);
//    }


    @PostMapping(value = "/salvar")
    public ResponseEntity<Equipamentos> salvar(String equipamentosJson,
                                               @RequestParam("files") List<MultipartFile> files) throws IOException {
        Equipamentos novoEquipamento = service.salvar(equipamentosJson, files);
        return ResponseEntity.created(URI.create("/equipamentos/" + novoEquipamento.getId())).body(novoEquipamento);
    }





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

    @GetMapping("/download/lista")
    public ResponseEntity<byte[]> downloadListaDocumentos(@RequestParam("ids") List<Long> ids) throws IOException {
        List<Documentos> documentos = documentosRepository.findAllById(ids);
        if (documentos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Cria um arquivo temporário para armazenar os documentos compactados
        File zipFile = File.createTempFile("documentos", ".zip");
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        for (Documentos documento : documentos) {
            Path caminho = Paths.get(documento.getCaminho());
            byte[] bytes = Files.readAllBytes(caminho);

            // Adiciona o documento ao arquivo compactado
            ZipEntry zipEntry = new ZipEntry(caminho.getFileName().toString());
            zos.putNextEntry(zipEntry);
            zos.write(bytes);
            zos.closeEntry();
        }
        zos.close();
        fos.close();
        // Lê o arquivo compactado em bytes
        byte[] zipBytes = Files.readAllBytes(zipFile.toPath());
        // Define o cabeçalho de resposta para o download do arquivo compactado
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFile.getName() + "\"");
        // Retorna a resposta com o arquivo compactado
        return ResponseEntity.ok()
                .headers(headers)
                .body(zipBytes);
    }




    @PutMapping(value = "/{equipamentoId}/alterar")
    public ResponseEntity<Equipamentos> alterarEquipamento(@PathVariable("equipamentoId") Long equipamentoId,
                                                           @RequestParam("equipamentosJson") String equipamentosJson,
                                                           @RequestParam("files") List<MultipartFile> files) throws IOException {
        Equipamentos equipamentoAtualizado = service.alterar(equipamentoId, equipamentosJson, files);
        return ResponseEntity.ok(equipamentoAtualizado);}

    @DeleteMapping("/{id}")
    public void removerEquipamento(@PathVariable Long id) {
        service.removerEquipamento(id);
    }

    @GetMapping("/pesquisar")
    public Page<Equipamentos> filtrarEquipamentos(@RequestParam(required = false) String anoCadastro,
                                                  @RequestParam(required = false) String tagEquipamento,
                                                  @RequestParam(required = false) String norma,
                                                  @RequestParam(required = false) String inspecaoExterna,
                                                  @RequestParam(required = false) String inspecaoInterna,
                                                  @RequestParam(required = false) String proximaInspecaoExterna,
                                                  @RequestParam(required = false) String proximaInspecaoInterna,
                                                  @RequestParam(required = false) String dataCalibracao,
                                                  @RequestParam(required = false) String proximaCalibracao,
                                                  @RequestParam(required = false) String inspecao,
                                                  @RequestParam(required = false) String proximaInspecao,
                                                  @RequestParam(required = false) Long idEmpresa,
                                                  @RequestParam(required = false) TipoEquipamentoEnum tipoEquipamento,
                                                  @RequestParam(required = false) String empresa,
                                                  Pageable pageable) {
        Specification<Equipamentos> specification = EquipamentosSpecification.filtrarEquipamentos(anoCadastro, tagEquipamento,
                norma, inspecaoExterna,
                inspecaoInterna,
                proximaInspecaoExterna,
                proximaInspecaoInterna,
                dataCalibracao,
                proximaCalibracao,
                inspecao,
                proximaInspecao,
                idEmpresa,
                tipoEquipamento, empresa);
        return equipamentosRepository.findAll(specification, pageable);
    }




}

