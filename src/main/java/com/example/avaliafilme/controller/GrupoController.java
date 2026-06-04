package com.example.avaliafilme.controller;

import com.example.avaliafilme.Model.GrupoModel;
import com.example.avaliafilme.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grupos")
public class GrupoController {

    @Autowired
    GrupoService grupoService;

    @GetMapping("/allGrupos")
    public List<GrupoModel> getAllGrupos() {
        return grupoService.getAllGrupos();
    }

    @PostMapping("/add")
    public GrupoModel addGrupo(@RequestBody GrupoModel grupo) {
        return grupoService.addGrupo(grupo);
    }

    @GetMapping("/grupo/{id}")
    public GrupoModel getGrupo(@PathVariable Long id) {
        return grupoService.getGrupoById(id);
    }

    @GetMapping("/buscar")
    public List<GrupoModel> buscarPorNome(@RequestParam String nome) {
        return grupoService.getGruposByNome(nome);
    }

    @PutMapping("/update/{id}")
    public GrupoModel updateGrupo(
            @RequestBody GrupoModel grupo,
            @PathVariable Long id) {
        return grupoService.updateGrupo(id, grupo);
    }

    @DeleteMapping("/grupo/{id}")
    public ResponseEntity.BodyBuilder deleteGrupo(@PathVariable Long id) {
        boolean deleted = grupoService.deleteGrupo(id);
        if (deleted)
            return ResponseEntity.status(HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{grupoId}/membros/{perfilId}")
    public GrupoModel adicionarMembro(
            @PathVariable Long grupoId,
            @PathVariable Long perfilId) {
        return grupoService.adicionarMembro(grupoId, perfilId);
    }

    @DeleteMapping("/{grupoId}/membros/{perfilId}")
    public GrupoModel removerMembro(
            @PathVariable Long grupoId,
            @PathVariable Long perfilId) {
        return grupoService.removerMembro(grupoId, perfilId);
    }

    @PostMapping("/{grupoId}/filmes/{filmeId}")
    public GrupoModel adicionarFilme(
            @PathVariable Long grupoId,
            @PathVariable Long filmeId) {
        return grupoService.adicionarFilme(grupoId, filmeId);
    }

    @DeleteMapping("/{grupoId}/filmes/{filmeId}")
    public GrupoModel removerFilme(
            @PathVariable Long grupoId,
            @PathVariable Long filmeId) {
        return grupoService.removerFilme(grupoId, filmeId);
    }
}