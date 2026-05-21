package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "filmes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmeModel {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String genero;

    @Column(nullable = false)
    private int anoLancamento;

    @Column(nullable = false, length = 100)
    private String diretor;

    @Column(length = 500)
    private String descricao;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dt_create;
}