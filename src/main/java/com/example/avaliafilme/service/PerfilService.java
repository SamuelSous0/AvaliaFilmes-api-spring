package com.example.avaliafilme.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.UserModel;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.UserRepository;
import com.example.avaliafilme.dto.PerfilRequestDTO;
import com.example.avaliafilme.dto.PerfilResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UserRepository userRepository;

    private PerfilResponseDTO toResponseDTO(PerfilModel perfil) {
        UserModel user = perfil.getUser();

        return new PerfilResponseDTO(
                perfil.getId(),
                user != null ? user.getId() : null,
                perfil.getBiografia(),
                perfil.getFotoUrl(),
                user != null ? user.getUsername() : null);
    }

    public PerfilResponseDTO addPerfil(PerfilRequestDTO perfilDto) {

        UserModel user = userRepository.findById(perfilDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + perfilDto.getUserId()));

        PerfilModel novoPerfil = new PerfilModel();
        novoPerfil.setBiografia(perfilDto.getBiografia());
        novoPerfil.setFotoUrl(perfilDto.getFotoUrl());
        novoPerfil.setUser(user);

        PerfilModel perfilGuardado = perfilRepository.save(novoPerfil);

        return toResponseDTO(perfilGuardado);
    }

    public List<PerfilResponseDTO> getAllPerfis() {
        List<PerfilModel> perfilLista = perfilRepository.findAll();
        List<PerfilResponseDTO> response = new ArrayList<>();

        for (PerfilModel p : perfilLista) {
            response.add(toResponseDTO(p));
        }
        return response;
    }

    public PerfilResponseDTO getPerfilById(Long id) {
        PerfilModel p = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com o id: " + id));

        return toResponseDTO(p);
    }

    public boolean deletePerfil(Long id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public PerfilResponseDTO updatePerfil(Long id, PerfilRequestDTO perfilDto) {
        PerfilModel perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com o id: " + id));

        if (perfilDto.getBiografia() != null) {
            perfil.setBiografia(perfilDto.getBiografia());
        }
        if (perfilDto.getFotoUrl() != null) {
            perfil.setFotoUrl(perfilDto.getFotoUrl());
        }

        PerfilModel perfilAtualizado = perfilRepository.save(perfil);

        return toResponseDTO(perfilAtualizado);
    }
}
