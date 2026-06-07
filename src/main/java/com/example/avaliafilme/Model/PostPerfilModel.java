package com.example.avaliafilme.Model;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Model.PerfilModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;;

@Entity
@Table(name = "post_perfil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostPerfilModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length= 500)
    private String descricao;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn( name = "perfil_id")
    @JsonIgnoreProperties({"user", "listas", "grupos", "biografia", "fotoUrl", "hibernateLazyInitializer", "handler"})
    private PerfilModel perfil;

    @ManyToOne
    @JoinColumn(name = "filme_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private FilmeModel filme;
}