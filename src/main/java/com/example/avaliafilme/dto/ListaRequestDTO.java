package com.example.avaliafilme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaRequestDTO {

    private String nome;
    private String descricao;
    private boolean publica;
    private Long criadorId;

}
