package com.example.model;

import jakarta.persistence.*;

@Table(name = "usuarios")
@Entity(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false , unique = true)
    private String email;

    public Usuario(){

    }

    public Usuario(String nome , String email) {

        if(nome == null || nome.isBlank()){
            throw  new IllegalArgumentException("O nome nao pode estar em branco!");
        }
        if(email == null || email.isBlank()){
            throw  new IllegalArgumentException("O email nao pode estar em branco!");
        }
        this.nome = nome;
        this.email = email;
    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }



    public String getEmail() {
        return email;
    }



}
