package com.project.fmproject.api.controller;

import com.project.fmproject.domain.model.Empresa;
import com.project.fmproject.domain.repository.EmpresaRepository;
import com.project.fmproject.domain.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    private final EmpresaRepository empresaRepository;

    public EmpresaController(EmpresaService empresaService, EmpresaRepository empresaRepository) {
        this.empresaService = empresaService;
        this.empresaRepository = empresaRepository;
    }


    @GetMapping("/buscarPorNome")
    public List<Empresa> buscarPorNome(@RequestParam String nome) {
        return empresaRepository.findByNomeContainingIgnoreCase(nome);
    }


    @GetMapping("/buscarPorCnpj")
    public ResponseEntity<Empresa> buscarPorCnpj(@RequestParam String cnpj) {
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);
        if (empresaOptional.isPresent()) {
            return ResponseEntity.ok(empresaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listarEmpresas();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarEmpresaPorId(@PathVariable Long id) {
        Empresa empresa = empresaService.buscarEmpresaPorId(id);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<Empresa> salvarEmpresa(@RequestBody Empresa empresa) {
        Empresa empresaSalva = empresaService.salvarEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
        Empresa empresa = empresaService.atualizarEmpresa(id, empresaAtualizada);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable Long id) {
        empresaService.deletarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}