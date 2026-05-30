package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "reacoes",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "review_id"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReacaoModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private int nota; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewModel review;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime dt_create;
}