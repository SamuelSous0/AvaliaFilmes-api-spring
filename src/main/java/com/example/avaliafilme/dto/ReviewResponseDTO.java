package com.example.avaliafilme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private Double nota;
    private String comentario;
    private LocalDateTime dataCriacao;
    private Long filmeId;
    private String filmeTitulo; 
    private Long perfilId;
    private String perfilNome;
}