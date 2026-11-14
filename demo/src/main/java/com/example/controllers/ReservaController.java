package com.example.controllers;

import com.example.DTO.dtoRequest.ReservaRequest;
import com.example.DTO.dtoResponse.ReservaResponse;
import com.example.model.Reserva;
import com.example.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> criar(@RequestBody @Valid ReservaRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        Reserva reserva = reservaService.criar(
                request.salaId(), request.usuarioId(), request.inicio(), request.fim());
        URI uri = uriBuilder.path("/reservas/{id}").buildAndExpand(reserva.getId()).toUri();
        return ResponseEntity.created(uri).body(ReservaResponse.fromEntity(reserva));
    }

    @GetMapping
    public List<ReservaResponse> listar() {
        return reservaService.listar().stream().map(ReservaResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public ReservaResponse buscarPorId(@PathVariable Long id) {
        return ReservaResponse.fromEntity(reservaService.buscarPorId(id));
    }

    @PatchMapping("/{id}/cancelar")
    public ReservaResponse cancelar(@PathVariable Long id) {
        return ReservaResponse.fromEntity(reservaService.cancelar(id));
    }
}