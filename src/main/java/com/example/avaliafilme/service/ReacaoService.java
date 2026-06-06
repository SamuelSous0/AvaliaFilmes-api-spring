package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.ReacaoModel;
import com.example.avaliafilme.Model.ReviewModel;
import com.example.avaliafilme.Model.UserModel;
import com.example.avaliafilme.Repository.ReacaoRepository;
import com.example.avaliafilme.Repository.ReviewRepository;
import com.example.avaliafilme.Repository.UserRepository;
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
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Transactional
    public ReacaoModel avaliar(Long userId, Long reviewId, int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve ser entre 1 e 5");
        }
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + userId));
        ReviewModel review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review não encontrada com id: " + reviewId));
        Optional<ReacaoModel> reacaoExistente = reacaoRepository.findByUser_IdAndReview_Id(userId, reviewId);
        if (reacaoExistente.isPresent()) {
            ReacaoModel reacao = reacaoExistente.get();
            reacao.setNota(nota);
            return reacaoRepository.save(reacao);
        }
        try {
            ReacaoModel reacao = ReacaoModel.builder()
                    .user(user)
                    .review(review)
                    .nota(nota)
                    .build();
            return reacaoRepository.save(reacao);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar avaliação: " + e.getMessage());
        }
    }

    @Transactional
    public boolean removerAvaliacao(Long userId, Long reviewId) {
        if (!reacaoRepository.existsByUser_IdAndReview_Id(userId, reviewId)) {
            return false;
        }
        try {
            reacaoRepository.deleteByUser_IdAndReview_Id(userId, reviewId);
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

    public List<ReacaoModel> getAvaliacoesByUser(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        try {
            return reacaoRepository.findByUser_Id(userId);
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