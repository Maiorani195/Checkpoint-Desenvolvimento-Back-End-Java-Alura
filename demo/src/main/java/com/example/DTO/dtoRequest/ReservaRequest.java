package com.example.DTO.dtoRequest;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaRequest(

        @NotNull(message = "O ID não pode estar vazio!")
        Long salaId,


        @NotNull(message = " O id do Usuario nao pode estar vazio!")
        Long usuarioId,

        @NotNull(message = "Precisa do horario do inicio")
        LocalDateTime inicio,

        @NotNull (message = "Precisa do horario do fim")
        LocalDateTime fim






        ){
}
