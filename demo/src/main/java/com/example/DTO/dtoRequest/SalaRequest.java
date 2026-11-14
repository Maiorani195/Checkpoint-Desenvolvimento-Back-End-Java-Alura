package com.example.DTO.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SalaRequest(

        @NotBlank
        String nome,

        @NotNull
        @Positive
        Integer capacidade






) {

}
