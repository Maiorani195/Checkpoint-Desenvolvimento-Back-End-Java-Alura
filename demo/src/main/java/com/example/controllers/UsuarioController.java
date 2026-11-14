package com.example.controllers;

import com.example.DTO.dtoRequest.UsuarioRequest;
import com.example.DTO.dtoResponse.UsuarioResponse;
import com.example.model.Usuario;
import com.example.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> criar(@RequestBody @Valid UsuarioRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioService.criar(request.nome(), request.email());
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(UsuarioResponse.fromEntity(usuario));
    }

    @GetMapping
    public List<UsuarioResponse> listar() {
        return usuarioService.listar().stream().map(UsuarioResponse::fromEntity).toList();
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscarPorId(@PathVariable Long id) {
        return UsuarioResponse.fromEntity(usuarioService.buscarPorId(id));
    }
}