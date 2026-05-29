package com.example.avaliafilme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.avaliafilme.Model.FavoritoModel;
import com.example.avaliafilme.service.FavoritoService;

@RestController
@RequestMapping("/api/v1/favoritos")
public class FavoritoController {
     @Autowired
    FavoritoService favoritoService;

    @PostMapping("/add")
    public FavoritoModel addFavorito(
            @RequestParam Long userId,
            @RequestParam Long filmeId) {

        return favoritoService.addFavorito(userId, filmeId);
    }

    @GetMapping("/user/{userId}")
    public List<FavoritoModel> getFavoritosByUser(@PathVariable Long userId) {

        return favoritoService.getFavoritosByUser(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.BodyBuilder deleteFavorito(@PathVariable Long id) {

        boolean deleted = favoritoService.deleteFavorito(id);

        if (deleted)
            return ResponseEntity.status(HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.NOT_FOUND);
    }
}

