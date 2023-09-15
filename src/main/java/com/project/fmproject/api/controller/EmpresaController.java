package com.project.fmproject.api.controller;

import com.project.fmproject.domain.model.Empresa;
import com.project.fmproject.domain.model.Usuario;
import com.project.fmproject.domain.repository.EmpresaRepository;
import com.project.fmproject.domain.service.EmpresaService;
import com.project.fmproject.domain.specification.EmpresaSpecification;
import com.project.fmproject.domain.specification.UsuariosSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
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
    public ResponseEntity<Page<Empresa>> listarEmpresas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Empresa> empresas = empresaService.listarEmpresas(pageable);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/lista-empresas")
    public ResponseEntity<List<Empresa>> listarEmpresas() {
        List<Empresa> empresas = empresaService.listEmpresas();
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

    @GetMapping("/pesquisar")
    public Page<Empresa> filtrarEmpresas(@RequestParam(value = "nome", required = false) String nome,
                                         @RequestParam(value = "cnpj", required = false) String cnpj,
                                         @RequestParam(value = "razaoSocial", required = false) String razaoSocial,
                                         Pageable pageable ) {
        Specification<Empresa> specification = EmpresaSpecification.filtrarEmpresas(nome, cnpj, razaoSocial);                     
        return empresaRepository.findAll(specification, pageable);                           
    }
}