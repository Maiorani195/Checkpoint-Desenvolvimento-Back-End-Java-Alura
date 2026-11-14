package com.example.model;

import com.example.model.enums.StatusReserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    private Sala sala;
    private Usuario usuario;

    @BeforeEach
    void preparar() {
        sala = new Sala("Sala 1", 10);
        usuario = new Usuario("Fernando", "fernando@email.com");
    }

    private LocalDateTime h(int hora, int minuto) {
        return LocalDateTime.of(2026, 11, 14, hora, minuto);
    }

    @Test
    void criaReservaAtivaQuandoDadosSaoValidos() {
        Reserva reserva = new Reserva(sala, usuario, h(10, 0), h(11, 0));

        assertEquals(StatusReserva.ATIVA, reserva.getStatus());
        assertEquals(sala, reserva.getSala());
        assertEquals(usuario, reserva.getUsuario());
        assertEquals(h(10, 0), reserva.getInicio());
        assertEquals(h(11, 0), reserva.getFim());
    }

    @Test
    void lancaExcecaoQuandoSalaForNula() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reserva(null, usuario, h(10, 0), h(11, 0)));
    }

    @Test
    void lancaExcecaoQuandoSalaEstiverInativa() {
        sala.desativar();

        assertThrows(IllegalArgumentException.class,
                () -> new Reserva(sala, usuario, h(10, 0), h(11, 0)));
    }

    @Test
    void lancaExcecaoQuandoUsuarioForNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reserva(sala, null, h(10, 0), h(11, 0)));
    }

    @Test
    void lancaExcecaoQuandoFimForAnteriorAoInicio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reserva(sala, usuario, h(11, 0), h(10, 0)));
    }

    @Test
    void lancaExcecaoQuandoFimForIgualAoInicio() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reserva(sala, usuario, h(10, 0), h(10, 0)));
    }

    @Test
    void lancaExcecaoQuandoDatasForemNulas() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reserva(sala, usuario, null, null));
    }

    @Test
    void cancelarMudaOStatusParaCancelada() {
        Reserva reserva = new Reserva(sala, usuario, h(10, 0), h(11, 0));

        reserva.cancelar();

        assertEquals(StatusReserva.CANCELADA, reserva.getStatus());
    }

    @Test
    void naoPermiteCancelarUmaReservaJaCancelada() {
        Reserva reserva = new Reserva(sala, usuario, h(10, 0), h(11, 0));
        reserva.cancelar();

        assertThrows(IllegalStateException.class, reserva::cancelar);
    }

    @Test
    void naoConflitaQuandoOFimDeUmaIgualAoInicioDaOutra() {
        Reserva existente = new Reserva(sala, usuario, h(10, 0), h(11, 0));

        assertFalse(existente.conflitaCom(h(11, 0), h(12, 0)));
    }

    @Test
    void conflitaNaSobreposicaoParcial() {
        Reserva existente = new Reserva(sala, usuario, h(10, 0), h(11, 0));

        assertTrue(existente.conflitaCom(h(10, 30), h(11, 30)));
    }

    @Test
    void conflitaQuandoANovaEstaContidaNaExistente() {
        Reserva existente = new Reserva(sala, usuario, h(10, 0), h(12, 0));

        assertTrue(existente.conflitaCom(h(10, 30), h(11, 0)));
    }

    @Test
    void conflitaQuandoOsHorariosSaoIdenticos() {
        Reserva existente = new Reserva(sala, usuario, h(10, 0), h(11, 0));

        assertTrue(existente.conflitaCom(h(10, 0), h(11, 0)));
    }

    @Test
    void reservaCanceladaNuncaConflita() {
        Reserva existente = new Reserva(sala, usuario, h(10, 0), h(11, 0));
        existente.cancelar();

        assertFalse(existente.conflitaCom(h(10, 0), h(11, 0)));
    }
}