package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.ReacaoModel;
import com.example.avaliafilme.Model.ReviewModel;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.ReacaoRepository;
import com.example.avaliafilme.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReacaoService {

    @Autowired
    ReacaoRepository reacaoRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Transactional
    public ReacaoModel avaliar(Long perfilId, Long reviewId, int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5");
        }
        PerfilModel perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com id: " + perfilId));

        ReviewModel review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review não encontrada com id: " + reviewId));
        Optional<ReacaoModel> reacaoExistente = reacaoRepository.findByPerfil_IdAndReview_Id(perfilId, reviewId);
        if (reacaoExistente.isPresent()) {
            ReacaoModel reacao = reacaoExistente.get();
            reacao.setNota(nota);
            return reacaoRepository.save(reacao);
        }
        try {
            ReacaoModel reacao = ReacaoModel.builder()
                    .perfil(perfil)
                    .review(review)
                    .nota(nota)
                    .build();
            return reacaoRepository.save(reacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar avaliação: " + e.getMessage());
        }
    }

    @Transactional
    public boolean removerAvaliacao(Long perfilId, Long reviewId) {
        if (!reacaoRepository.existsByPerfil_IdAndReview_Id(perfilId, reviewId)) {
            return false;
        }
        try {
            reacaoRepository.deleteByPerfil_IdAndReview_Id(perfilId, reviewId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover avaliação: " + e.getMessage());
        }
    }

    public List<ReacaoModel> getAvaliacoesByReview(Long reviewId) {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        try {
            return reacaoRepository.findByReview_Id(reviewId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar avaliações: " + e.getMessage());
        }
    }

    public List<ReacaoModel> getAvaliacoesByPerfil(Long perfilId) {
        if (perfilId == null || perfilId <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        try {
            return reacaoRepository.findByPerfil_Id(perfilId);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar avaliações: " + e.getMessage());
        }
    }

    public Double getMediaByReview(Long reviewId) {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        try {
            Double media = reacaoRepository.calcularMediaByReviewId(reviewId);
            return media != null ? media : 0.0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular média: " + e.getMessage());
        }
    }
}