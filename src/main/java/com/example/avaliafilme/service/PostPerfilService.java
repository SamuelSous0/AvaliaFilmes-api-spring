package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.PostPerfilModel;
import com.example.avaliafilme.Repository.PostPerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostPerfilService{

    @Autowired
    private PostPerfilRepository repository;

    public List<PostPerfilModel> listarTodos(){
        return repository.findAll();
    }
    public List<PostPerfilModel> listarPortPerfil(Long perfilId){
        return repository.findByPerfilId(perfilId);
    }
    public Optional<PostPerfilModel> buscarPorId(Long id){
        return repository.findById(id);
    }
    public PostPerfilModel criar(PostPerfilModel postPerfil){
        return repository.save(postPerfil);
    }
    public PostPerfilModel att (Long id, PostPerfilModel dadosAtt){
        PostPerfilModel post = repository.findByPerfilId(id)
            .orElseThrow(()-> new RuntimeException("Post Perfil não encontrado"));
        
            post.setDescricao(dadosAtt.getDescricao());
            post.setFilme(dadosAtt.getFilme());
            return repository.save(post);
    }
    public void deletar(Long id){
        repository.deleteById(id);
    }
}