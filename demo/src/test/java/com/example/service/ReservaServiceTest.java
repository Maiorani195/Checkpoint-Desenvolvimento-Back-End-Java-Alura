package com.example.service;

import com.example.exception.ConflitoDeReservaException;
import com.example.model.Reserva;
import com.example.model.Sala;
import com.example.model.Usuario;
import com.example.model.enums.StatusReserva;
import com.example.repository.ReservaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private SalaService salaService;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void lancaExcecaoESemSalvarQuandoOBancoApontaConflito() {
        Sala sala = new Sala("Sala 1", 10);
        Usuario usuario = new Usuario("Fernando", "fernando@email.com");
        LocalDateTime inicio = LocalDateTime.of(2026, 11, 14, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2026, 11, 14, 11, 0);

        when(salaService.buscarPorId(1L)).thenReturn(sala);
        when(usuarioService.buscarPorId(1L)).thenReturn(usuario);
        when(reservaRepository.existeConflito(1L, StatusReserva.ATIVA, inicio, fim))
                .thenReturn(true);

        assertThrows(ConflitoDeReservaException.class,
                () -> reservaService.criar(1L, 1L, inicio, fim));

        verify(reservaRepository, never()).save(any(Reserva.class));
    }
}