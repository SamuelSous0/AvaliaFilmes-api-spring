package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;;

@Enity
@Table(name = "post_perfil")
@NoArgsConstructor
@AllArgsConstructor
@BodyBuilder

public class PostPerfilModel{
    @Id
    @GeneratedValue
    private Long id;

    @Column(length= 500)
    private String descricao;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn( name = "perfil_id")
    private PerfilModel perfil;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    private FilmeModel filme;
}