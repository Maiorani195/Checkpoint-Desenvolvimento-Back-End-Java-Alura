package com.example.service;

import com.example.exception.ConflitoDeReservaException;
import com.example.exception.RecursoNaoEncontradoException;
import com.example.model.Reserva;
import com.example.model.Sala;
import com.example.model.Usuario;
import com.example.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaService salaService;
    private final UsuarioService usuarioService;

    public ReservaService(ReservaRepository reservaRepository,
                          SalaService salaService,
                          UsuarioService usuarioService) {
        this.reservaRepository = reservaRepository;
        this.salaService = salaService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    public Reserva criar(Long salaId, Long usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        Sala sala = salaService.buscarPorId(salaId);          // 404 se não existir
        Usuario usuario = usuarioService.buscarPorId(usuarioId); // 404 se não existir


        Reserva nova = new Reserva(sala, usuario, inicio, fim);

        List<Reserva> existentes = reservaRepository.findBySalaId(salaId);
        for (Reserva existente : existentes) {
            if (existente.conflitaCom(inicio, fim)) { // ignora canceladas
                throw new ConflitoDeReservaException("A sala já está reservada no período informado!");
            }
        }
        return reservaRepository.save(nova);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reserva buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Reserva não encontrada: " + id));
    }

    @Transactional
    public Reserva cancelar(Long id) {
        Reserva reserva = buscarPorId(id);
        reserva.cancelar();
        return reserva;
    }
}