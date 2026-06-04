package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grupos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private UserModel criador;

    @ManyToMany
    @JoinTable(
        name = "grupo_membro",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    @Builder.Default
    private List<PerfilModel> membros = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "grupo_filme",
        joinColumns = @JoinColumn(name = "grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    @Builder.Default
    private List<FilmeModel> filmes = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dt_create;
}