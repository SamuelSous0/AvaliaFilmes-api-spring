package com.example.avaliafilme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avaliafilme.Model.FavoritoModel;
import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Model.UserModel;
import com.example.avaliafilme.Repository.FavoritoRepository;
import com.example.avaliafilme.Repository.FilmeRepository;
import com.example.avaliafilme.Repository.UserRepository;

@Service
public class FavoritoService {
    
    @Autowired
    FavoritoRepository favoritoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FilmeRepository filmeRepository;

    public FavoritoModel addFavorito(Long userId, Long filmeId) {
        if (favoritoRepository.existsByUsuarioIdAndFilmeId(userId, filmeId)) {
            throw new RuntimeException(
                "Este filme já está nos favoritos do usuário"
            );
        }

        UserModel usuario = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Usuário não encontrado"));

        FilmeModel filme = filmeRepository.findById(filmeId)
                .orElseThrow(() ->
                        new RuntimeException("Filme não encontrado"));

        FavoritoModel favorito = FavoritoModel.builder()
                .usuario(usuario)
                .filme(filme)
                .build();

        return favoritoRepository.save(favorito);
    }

    public List<FavoritoModel> getFavoritosByUser(Long userId) {

        return favoritoRepository.findByUsuarioId(userId);
    }

    public boolean deleteFavorito(Long id) {

        if (favoritoRepository.existsById(id)) {

            favoritoRepository.deleteById(id);

            return true;
        }

        return false;
    }
}

