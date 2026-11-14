package com.example.service;

import com.example.exception.RecursoDuplicadoException;
import com.example.exception.RecursoNaoEncontradoException;
import com.example.model.Sala;
import com.example.repository.SalaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Transactional
    public Sala criar(String nome, int capacidade) {
        if (salaRepository.existsByNome(nome)) {
            throw new RecursoDuplicadoException("Já existe uma sala com esse nome!");
        }

        return salaRepository.save(new Sala(nome, capacidade));
    }

    @Transactional(readOnly = true)
    public List<Sala> listar() {
        return salaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Sala não encontrada: " + id));
    }

    @Transactional
    public Sala atualizar(Long id, String nome, int capacidade) {
        Sala sala = buscarPorId(id);

        if (!sala.getNome().equals(nome) && salaRepository.existsByNome(nome)) {
            throw new RecursoDuplicadoException("Já existe uma sala com esse nome!");
        }
        sala.alterarNome(nome);
        sala.alterarCapacidade(capacidade);
        return sala;
    }

    @Transactional
    public Sala ativar(Long id) {
        Sala sala = buscarPorId(id);
        sala.ativar();
        return sala;
    }

    @Transactional
    public Sala desativar(Long id) {
        Sala sala = buscarPorId(id);
        sala.desativar();
        return sala;
    }
}