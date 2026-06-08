package com.example.avaliafilme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PerfilResponseDTO {

    private long id;
    private Long userId;
    private String biografia;
    private String fotoUrl;
    private String username;
}
