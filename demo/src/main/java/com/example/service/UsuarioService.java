package com.example.service;

import com.example.exception.RecursoDuplicadoException;
import com.example.exception.RecursoNaoEncontradoException;
import com.example.model.Usuario;
import com.example.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario criar(String nome, String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RecursoDuplicadoException("Já existe um usuário com esse e-mail!");
        }
        //
        return usuarioRepository.save(new Usuario(nome, email));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
    }
}