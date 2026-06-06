package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(
    name = "reacoes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"perfil_id", "review_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id", nullable = false)
    private PerfilModel perfil;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewModel review;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dt_create;
}