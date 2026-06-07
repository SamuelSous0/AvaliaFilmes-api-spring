package com.example.avaliafilme.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    @JsonIgnoreProperties({"listas", "grupos", "hibernateLazyInitializer", "handler"})
    private PerfilModel perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    @JsonIgnoreProperties({"perfil", "filme", "hibernateLazyInitializer", "handler"})
    private ReviewModel review;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dt_create;
}