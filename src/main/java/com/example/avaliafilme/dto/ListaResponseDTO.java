package com.example.avaliafilme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private boolean publica;
    private String criadorUsername;
    private int quantidadeFilmes;
    private List<String> colaboradores;
    private LocalDateTime criadoEm;
}
