package com.example.avaliafilme.controller;

import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.PostPerfilModel;
import com.example.avaliafilme.PostPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post-perfil")
public class PostPerfilController {
    @Autowired
    private PostPerfilService service;

    @GetMapping
    public List<PostPerfilModel> listarTodos(){
        return service.listarTodos();
    }

    @GetMapping("/perfil/{perfilId}")
    public List<PostPerfilModel> listarPorPerfil(@PathVariable Long perfilId){
        return service.listarPorPerfil(perfilId);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostPerfilModel> buscarporId(@PathVariable Long id){
        return service.buscarporId(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.NOT_FOUND().build());
    }
    @PostMapping
    public PostPerfilModel criar (@RequestBody PostPerfilModel postPerfil){
        return service.criar(postPerfil);
    }
    @PutMapping ("/{id}")
    public ResponseEntity<PostPerfilModel> atualizar (@PathVariable Long id, @RequestBody PostPerfilModel dadosAtt){
        try{
            return ResponseEntity.ok(service.atualizar(id, dadosAtt));    
        } catch(RuntimeException e) {
            return ResponseEntity.NOT_FOUND().build();
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}