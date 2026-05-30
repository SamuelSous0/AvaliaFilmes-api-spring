package com.example.avaliafilme.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "lista")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListaFilmesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 100)
    private String descricao;

    @Column(nullable = false)
    private boolean publica;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private UserModel criador;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dt_create;

    @ManyToMany
    @JoinTable(name = "lista_filme",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id"))
    private List<FilmeModel> filmes;


    @ManyToMany
    @JoinTable(name = "lista_perfil",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id"))
    private List<PerfilModel> colaboradores;
}