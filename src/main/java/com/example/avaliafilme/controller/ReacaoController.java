package com.example.avaliafilme.controller;

import com.example.avaliafilme.Model.ReacaoModel;
import com.example.avaliafilme.service.ReacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reacoes")
public class ReacaoController {

    @Autowired
    ReacaoService reacaoService;

    @PostMapping("/avaliar")
    public ReacaoModel avaliar(
            @RequestParam Long userId,
            @RequestParam Long reviewId,
            @RequestParam int nota) {
        return reacaoService.avaliar(userId, reviewId, nota);
    }

    @DeleteMapping("/remover")
    public ResponseEntity.BodyBuilder removerAvaliacao(
            @RequestParam Long userId,
            @RequestParam Long reviewId) {
        boolean removed = reacaoService.removerAvaliacao(userId, reviewId);
        if (removed)
            return ResponseEntity.status(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/review/{reviewId}")
    public List<ReacaoModel> getAvaliacoesByReview(@PathVariable Long reviewId) {
        return reacaoService.getAvaliacoesByReview(reviewId);
    }

    @GetMapping("/user/{userId}")
    public List<ReacaoModel> getAvaliacoesByUser(@PathVariable Long userId) {
        return reacaoService.getAvaliacoesByUser(userId);
    }

    @GetMapping("/review/{reviewId}/media")
    public Double getMediaByReview(@PathVariable Long reviewId) {
        return reacaoService.getMediaByReview(reviewId);
    }
}