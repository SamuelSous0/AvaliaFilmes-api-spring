package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.ReviewModel;
import com.example.avaliafilme.Repository.FilmeRepository;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.ReviewRepository;
import com.example.avaliafilme.dto.ReviewRequestDTO;
import com.example.avaliafilme.dto.ReviewResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    public ReviewResponseDTO criarReview(ReviewRequestDTO request) {
        FilmeModel filme = filmeRepository.findById(request.getFilmeId())
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        PerfilModel perfil = perfilRepository.findById(request.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        ReviewModel review = ReviewModel.builder()
                .nota(request.getNota())
                .comentario(request.getComentario())
                .filme(filme)
                .perfil(perfil)
                .build();

        ReviewModel salva = reviewRepository.save(review);
        return converterParaResponseDTO(salva);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> listarTodas() {
        return reviewRepository.findAll().stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> listarPorFilme(Long filmeId) {
        return reviewRepository.findByFilmeId(filmeId).stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewResponseDTO atualizarReview(Long id, ReviewRequestDTO request) {
        ReviewModel review = reviewRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Review não encontrada"
        ));

        if (request.getFilmeId() != null) {
            FilmeModel filme = filmeRepository.findById(request.getFilmeId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Filme não encontrado"
                    ));
            review.setFilme(filme);
        }

        if (request.getPerfilId() != null) {
            PerfilModel perfil = perfilRepository.findById(request.getPerfilId())
        .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Perfil não encontrado"
        ));
            review.setPerfil(perfil);
        }

        if (request.getNota() != null) {
            review.setNota(request.getNota());
        }

        if (request.getComentario() != null) {
            review.setComentario(request.getComentario());
        }

        ReviewModel atualizada = reviewRepository.save(review);
        return converterParaResponseDTO(atualizada);
    }

    public void deletarReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review não encontrada");
        }
        reviewRepository.deleteById(id);
    }

    private ReviewResponseDTO converterParaResponseDTO(ReviewModel model) {
        return ReviewResponseDTO.builder()
                .id(model.getId())
                .nota(model.getNota())
                .comentario(model.getComentario())
                .dataCriacao(model.getDataCriacao())
                .filmeId(model.getFilme().getId())
                .filmeTitulo(model.getFilme().getTitulo())
                .perfilId(model.getPerfil().getId())
                .perfilNome(model.getPerfil().getUser().getUsername())
                .build();
    }
}