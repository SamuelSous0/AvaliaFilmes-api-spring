package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.PostPerfilModel;
import com.example.avaliafilme.Repository.FilmeRepository;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.PostPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostPerfilService{

    @Autowired
    private PostPerfilRepository repository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    public List<PostPerfilModel> listarTodos(){
        return repository.findAll();
    }
    public List<PostPerfilModel> listarPorPerfil(Long perfilId){
        return repository.findByPerfilId(perfilId);
    }
    public Optional<PostPerfilModel> buscarporId(Long id){
        return repository.findById(id);
    }
    public PostPerfilModel criar(PostPerfilModel postPerfil){
        PerfilModel perfil = perfilRepository.findById(
            postPerfil.getPerfil().getId()
        ).orElseThrow(() -> new RuntimeException("Perfil não encontrado."));

        FilmeModel filme = filmeRepository.findById(
            postPerfil.getFilme().getId()
        ).orElseThrow(() -> new RuntimeException("Filme não encontrado."));
        
        postPerfil.setPerfil(perfil);
        postPerfil.setFilme(filme);

        return repository.save(postPerfil);  
    }
    public PostPerfilModel atualizar (Long id, PostPerfilModel dadosAtt){
        PostPerfilModel post = repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Post Perfil não encontrado"));
        
            post.setDescricao(dadosAtt.getDescricao());
            post.setFilme(dadosAtt.getFilme());
            
            return repository.save(post);
    }
    public void deletar(Long id){
        repository.deleteById(id);
    }
}