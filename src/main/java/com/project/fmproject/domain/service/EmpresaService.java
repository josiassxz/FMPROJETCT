package com.project.fmproject.domain.service;


import com.project.fmproject.domain.exception.EntidadeNaoEncontradaException;
import com.project.fmproject.domain.model.Empresa;
import com.project.fmproject.domain.model.Usuario;
import com.project.fmproject.domain.repository.EmpresaRepository;
import com.project.fmproject.domain.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    private final UsuarioRepository usuarioRepository;

    public EmpresaService(EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Empresa> listarEmpresas(Pageable pageable) {
        return empresaRepository.findAll(pageable);
    }

    public Empresa buscarEmpresaPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empresa não encontrada"));
    }

    public Empresa salvarEmpresa(Empresa empresa) {
        List<Usuario> usuarios = new ArrayList<>();
        if (empresa.getIdsUsuarios() != null) {
            for (Long id : empresa.getIdsUsuarios()) {
                usuarios.add(usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado com o ID informado: " + id)));
            }
        }
        empresa.setUsuarios(usuarios);
        return empresaRepository.save(empresa);
    }

    public Empresa atualizarEmpresa(Long id, Empresa empresaAtualizada) {
        Empresa empresaExistente = buscarEmpresaPorId(id);
        empresaExistente.setNome(empresaAtualizada.getNome());
        empresaExistente.setRazaoSocial(empresaAtualizada.getRazaoSocial());
        empresaExistente.setCnpj(empresaAtualizada.getCnpj());
        empresaExistente.setEmail(empresaAtualizada.getEmail());
        empresaExistente.setTelefone(empresaAtualizada.getTelefone());
        empresaExistente.setUsuarios(empresaAtualizada.getUsuarios());
        return empresaRepository.save(empresaExistente);
    }

    public void deletarEmpresa(Long id) {
        empresaRepository.deleteById(id);
    }


}
