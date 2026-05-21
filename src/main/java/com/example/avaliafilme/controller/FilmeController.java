package com.example.avaliafilme.controller;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.service.FilmeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filmes")
public class FilmeController {

    @Autowired
    FilmeService filmeService;

    @GetMapping("/allFilmes")
    public List<FilmeModel> getAllFilmes() {

        return filmeService.getAllFilmes();
    }

    @PostMapping("/add")
    public FilmeModel newFilme(@RequestBody FilmeModel filme) {

        return filmeService.addFilme(filme);
    }

    @GetMapping("/filme/{id}")
    public FilmeModel getFilme(@PathVariable Long id) {

        return filmeService.getFilmeById(id);
    }

    @PutMapping("/update/{id}")
    public FilmeModel updateFilme(
            @RequestBody FilmeModel filme,
            @PathVariable Long id) {

        return filmeService.updateFilme(id, filme);
    }

    @DeleteMapping("/filme/{id}")
    public ResponseEntity.BodyBuilder deleteFilme(@PathVariable Long id) {

        boolean deleted = filmeService.deleteFilme(id);

        if (deleted)
            return ResponseEntity.status(HttpStatus.OK);

        return ResponseEntity.status(HttpStatus.NOT_FOUND);
    }
}