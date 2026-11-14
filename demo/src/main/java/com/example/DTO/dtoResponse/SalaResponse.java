package com.example.DTO.dtoResponse;

import com.example.model.Sala;

public record SalaResponse(
        Long id,
        String nome ,
        Integer capacidade,
        Boolean ativa

)


{
    public static SalaResponse fromEntity(Sala sala){
        return  new SalaResponse(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.isAtiva()
        );
    }

}
