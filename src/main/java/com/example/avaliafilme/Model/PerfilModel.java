package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "perfis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PerfilModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 255)
    private String biografia;

    @Column(name = "foto_url", length = 500)
    private String fotoUrl;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserModel user;

    @ManyToMany(mappedBy = "colaboradores")
    private List<ListaFilmesModel> listas;
}
