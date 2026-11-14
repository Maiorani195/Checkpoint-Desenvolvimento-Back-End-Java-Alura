package com.example.model;

import jakarta.persistence.*;

@Table(name = "salas")
@Entity(name = "Sala")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private int capacidade;

    @Column(nullable = false)
    private boolean ativa = true;

    public Sala() {
    }

    public Sala(String nome, int capacidade) {
        validarNome(nome);
        validarCapacidade(capacidade);
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public void alterarNome(String nome) {
        validarNome(nome);
        this.nome = nome;
    }

    public void alterarCapacidade(int capacidade) {
        validarCapacidade(capacidade);
        this.capacidade = capacidade;
    }

    public void ativar() {
        this.ativa = true;
    }

    public void desativar() {
        this.ativa = false;
    }

    private static void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da sala é obrigatório");
        }
    }

    private static void validarCapacidade(int capacidade) {
        if (capacidade <= 0) ;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isAtiva() {
        return ativa;
    }
}