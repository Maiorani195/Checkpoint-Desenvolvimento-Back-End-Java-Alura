package com.example.DTO.dtoResponse;

import com.example.model.Reserva;
import com.example.model.enums.StatusReserva;

import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,
        Long salaId,
        Long usuarioId,
        LocalDateTime inicio,
        LocalDateTime fim,
        StatusReserva status
) {

    public static ReservaResponse fromEntity(Reserva reserva) {
        return new ReservaResponse(
                reserva.getId(),
                reserva.getSala().getId(),
                reserva.getUsuario().getId(),
                reserva.getInicio(),
                reserva.getFim(),
                reserva.getStatus()
        );
    }
}