package com.example.controllers;

import com.example.DTO.dtoRequest.SalaRequest;
import com.example.DTO.dtoResponse.SalaResponse;
import com.example.model.Sala;
import com.example.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping
    public ResponseEntity<SalaResponse> criar(@RequestBody @Valid SalaRequest request,
                                              UriComponentsBuilder uriBuilder) {
        Sala sala = salaService.criar(request.nome(), request.capacidade());
        URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(sala.getId()).toUri();
        return ResponseEntity.created(uri).body(SalaResponse.fromEntity(sala));
    }

    @GetMapping
    public List<SalaResponse> listar() {
        return salaService.listar().stream().map(SalaResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public SalaResponse buscarPorId(@PathVariable Long id) {
        return SalaResponse.fromEntity(salaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public SalaResponse atualizar(@PathVariable Long id, @RequestBody @Valid SalaRequest request) {
        return SalaResponse.fromEntity(salaService.atualizar(id, request.nome(), request.capacidade()));
    }

    @PatchMapping("/{id}/ativar")
    public SalaResponse ativar(@PathVariable Long id) {
        return SalaResponse.fromEntity(salaService.ativar(id));
    }

    @PatchMapping("/{id}/desativar")
    public SalaResponse desativar(@PathVariable Long id) {
        return SalaResponse.fromEntity(salaService.desativar(id));
    }
}