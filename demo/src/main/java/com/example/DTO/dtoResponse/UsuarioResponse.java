package com.example.DTO.dtoResponse;

import com.example.model.Usuario;

public record UsuarioResponse(
        Long id,
        String nome,
        String email
)
{
    public static UsuarioResponse fromEntity(Usuario usuario){
        return  new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }
}
