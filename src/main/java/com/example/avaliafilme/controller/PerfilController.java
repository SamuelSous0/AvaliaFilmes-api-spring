package com.example.avaliafilme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.avaliafilme.dto.PerfilRequestDTO;
import com.example.avaliafilme.dto.PerfilResponseDTO;
import com.example.avaliafilme.service.PerfilService;

@RestController
@RequestMapping("/api/v1/perfis")

public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/all")
    public List<PerfilResponseDTO> getAllPerfis() {
        return perfilService.getAllPerfis();
    }

    @PostMapping("/add")
    public PerfilResponseDTO newPerfil(@RequestBody PerfilRequestDTO perfil) {
        return perfilService.addPerfil(perfil);
    }

    @GetMapping("/{id}")
    public PerfilResponseDTO getPerfil(@PathVariable Long id) {
        return perfilService.getPerfilById(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity.BodyBuilder deletePerfil(@PathVariable Long id) {
        boolean deletado = perfilService.deletePerfil(id);

        if (deletado) {
            return ResponseEntity.status(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND);

    }
}
