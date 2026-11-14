package com.example.exception;

public class ConflitoDeReservaException extends  RuntimeException{

    public ConflitoDeReservaException(String mensagem){
        super(mensagem);
    }
}
