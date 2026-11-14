package com.example.model;

import com.example.model.enums.StatusReserva;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "reservas")
@Entity(name = "Reserva")
public class Reserva {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    @Column(nullable = false)
    private LocalDateTime inicio;


    @Column(nullable = false)
    private LocalDateTime fim;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReserva status;


    protected Reserva() {
    }

    public Reserva(Sala sala, Usuario usuario, LocalDateTime inicio, LocalDateTime fim) {
        if (sala == null) {
            throw new IllegalArgumentException("A sala é obrigatória!");
        }
        if (!Boolean.TRUE.equals(sala.isAtiva())) {
            throw new IllegalArgumentException("Não é possível reservar uma sala inativa!");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("O usuário é obrigatório!");
        }
        if (inicio == null || fim == null || !fim.isAfter(inicio)) {
            throw new IllegalArgumentException("O fim deve ser posterior ao início!");
        }

        this.sala = sala;
        this.usuario = usuario;
        this.inicio = inicio;
        this.fim = fim;
        this.status = StatusReserva.ATIVA;
    }


    public void cancelar() {
        if (!status.podeCancelar()) {
            throw new IllegalStateException("Só reservas ativas podem ser canceladas!");
        }
        this.status = StatusReserva.CANCELADA;
    }


    public boolean conflitaCom(LocalDateTime outroInicio, LocalDateTime outroFim) {
        if (!status.ocupaHorario()) {
            return false;
        }
        return inicio.isBefore(outroFim) && fim.isAfter(outroInicio);
    }

    public Long getId() {
        return id;
    }

    public Sala getSala() {
        return sala;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public StatusReserva getStatus() {
        return status;
    }
}