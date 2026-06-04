package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "filmes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmeModel {

    @Id
    @GeneratedValue
    private Long id;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "filmes")
    private List<ListaFilmesModel> listas;
}