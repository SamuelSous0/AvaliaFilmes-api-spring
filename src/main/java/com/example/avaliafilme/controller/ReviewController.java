package com.example.avaliafilme.controller;

import com.example.avaliafilme.dto.ReviewRequestDTO;
import com.example.avaliafilme.dto.ReviewResponseDTO;
import com.example.avaliafilme.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> criarReview(@RequestBody ReviewRequestDTO request) {
        ReviewResponseDTO response = reviewService.criarReview(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDTO>> listarTodas() {
        List<ReviewResponseDTO> reviews = reviewService.listarTodas();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<List<ReviewResponseDTO>> listarPorFilme(@PathVariable Long filmeId) {
        List<ReviewResponseDTO> reviews = reviewService.listarPorFilme(filmeId);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> atualizarReview(
            @PathVariable Long id,
            @RequestBody ReviewRequestDTO request
    ) {
        ReviewResponseDTO response = reviewService.atualizarReview(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReview(@PathVariable Long id) {
        reviewService.deletarReview(id);
        return ResponseEntity.noContent().build();
    }
}