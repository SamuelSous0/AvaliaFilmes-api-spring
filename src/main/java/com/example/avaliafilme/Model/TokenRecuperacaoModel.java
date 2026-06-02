package com.example.avaliafilme.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokens_recuperacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRecuperacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;

    @Column(name = "valido_ate", nullable = false)
    private LocalDateTime validoAte;

    @Column(name = "utilizado_em")
    private LocalDateTime utilizadoEm;
}