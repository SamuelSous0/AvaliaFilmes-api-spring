package com.example.avaliafilme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDTO {
    private Double nota;
    private String comentario;
    private Long filmeId;
    private Long perfilId;
}