package com.example.model.enums;

public enum StatusReserva {



    ATIVA,

    CANCELADA;




    public Boolean ocupaHorario(){
        return this == ATIVA;
    }


    public Boolean podeCancelar(){
        return  this == ATIVA;
    }
}

