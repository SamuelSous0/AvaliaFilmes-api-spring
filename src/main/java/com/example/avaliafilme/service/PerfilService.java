package com.example.avaliafilme.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.userModel;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.userRepository;
import com.example.avaliafilme.dto.PerfilRequestDTO;
import com.example.avaliafilme.dto.PerfilResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private userRepository userRepository;

    public PerfilResponseDTO addPerfil(PerfilRequestDTO perfilDto) {

        userModel user = userRepository.findById(perfilDto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o id: " + perfilDto.getUserId()));

        PerfilModel novoPerfil = new PerfilModel();
        novoPerfil.setBiografia(perfilDto.getBiografia());
        novoPerfil.setFotoUrl(perfilDto.getFotoUrl());
        novoPerfil.setUser(user);

        PerfilModel perfilGuardado = perfilRepository.save(novoPerfil);

        return new PerfilResponseDTO(perfilGuardado.getId(), perfilGuardado.getBiografia(), perfilGuardado.getFotoUrl(),
                perfilGuardado.getUser().getUsername());

    }

    public List<PerfilResponseDTO> getAllPerfis() {
        List<PerfilModel> perfilLista = perfilRepository.findAll();
        List<PerfilResponseDTO> response = new ArrayList<>();

        for (PerfilModel p : perfilLista) {
            response.add(new PerfilResponseDTO(p.getId(), p.getBiografia(), p.getFotoUrl(), p.getUser().getUsername()));
        }
        return response;
    }

    public PerfilResponseDTO getPerfilById(Long id) {
        PerfilModel p = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com o id: " + id));

        return new PerfilResponseDTO(p.getId(), p.getBiografia(), p.getFotoUrl(), p.getUser().getUsername());
    }

    public boolean deletePerfil(Long id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
