package com.example.repository;

import com.example.model.Reserva;
import com.example.model.enums.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva , Long> {

    List<Reserva> findBySalaId(Long salaId);
    @Query("""
            SELECT COUNT(r) > 0
            FROM Reserva r
            WHERE r.sala.id = :salaId
              AND r.status = :status
              AND r.inicio < :fim
              AND r.fim > :inicio
            """)

    boolean existeConflito(@Param("salaId") Long salaId,
                           @Param("status") StatusReserva status,
                           @Param("inicio") LocalDateTime inicio,
                           @Param("fim") LocalDateTime fim);
}
