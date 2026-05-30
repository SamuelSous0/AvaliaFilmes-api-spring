package com.example.avaliafilme.controller;

import com.example.avaliafilme.dto.AdicionarColaboradorDTO;
import com.example.avaliafilme.dto.AdicionarFilmeDTO;
import com.example.avaliafilme.dto.ListaRequestDTO;
import com.example.avaliafilme.dto.ListaResponseDTO;
import com.example.avaliafilme.service.ListaFilmesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas")
@RequiredArgsConstructor
public class ListaFilmesController {

    private final ListaFilmesService listaService;

    @PostMapping
    public ResponseEntity<ListaResponseDTO> criar(@RequestBody @Valid ListaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(listaService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ListaResponseDTO>> buscarTodas() {
        return ResponseEntity.ok(listaService.buscarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(listaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ListaRequestDTO dto) {
        return ResponseEntity.ok(listaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        listaService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{id}/filmes")
    public ResponseEntity<ListaResponseDTO> adicionarFilme(
            @PathVariable Long id,
            @RequestBody @Valid AdicionarFilmeDTO dto) {
        return ResponseEntity.ok(listaService.adicionarFilme(id, dto));
    }

    @PostMapping("/{id}/colaboradores")
    public ResponseEntity<ListaResponseDTO> adicionarColaborador(
            @PathVariable Long id,
            @RequestBody @Valid AdicionarColaboradorDTO dto) {
        return ResponseEntity.ok(listaService.adicionarColaborador(id, dto));
    }

    @GetMapping("/{id}/filmes")
    public ResponseEntity<List<String>> buscarFilmes(@PathVariable Long id) {
        return ResponseEntity.ok(listaService.buscarFilmesDaLista(id));
    }
}
