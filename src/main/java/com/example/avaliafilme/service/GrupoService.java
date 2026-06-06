package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.GrupoModel;
import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Repository.FilmeRepository;
import com.example.avaliafilme.Repository.GrupoRepository;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GrupoService {

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    FilmeRepository filmeRepository;

    public GrupoModel addGrupo(GrupoModel grupo) {

        if (grupo.getNome() == null || grupo.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do grupo não pode ser vazio.");
        }
        if (grupo.getCriador() == null) {
            throw new IllegalArgumentException("O grupo precisa ter um criador.");
        }
        if (grupo.getCriador().getId() <= 0) {
            throw new IllegalArgumentException("O grupo precisa ter um criador válido.");
        }

        userRepository.findById((long)grupo.getCriador().getId())
                .orElseThrow(() -> new RuntimeException(
                        "Usuário não encontrado com id: " + grupo.getCriador().getId()));
        try {
            return grupoRepository.save(grupo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar grupo: " + e.getMessage());
        }
    }

    public List<GrupoModel> getAllGrupos() {
        try {
            return grupoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar grupos: " + e.getMessage());
        }
    }

    public GrupoModel getGrupoById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        try {
            return grupoRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Grupo não encontrado com id: " + id));
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar grupo: " + e.getMessage());
        }
    }

    public List<GrupoModel> getGruposByNome(String nome) {
        try {
            return grupoRepository.findByNomeContainingIgnoreCase(nome);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar grupos: " + e.getMessage());
        }
    }

    public GrupoModel updateGrupo(Long id, GrupoModel data) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        try {
            GrupoModel grupo = grupoRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Grupo não encontrado com id: " + id));

            grupo.setNome(data.getNome());
            grupo.setDescricao(data.getDescricao());

            return grupoRepository.save(grupo);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar grupo: " + e.getMessage());
        }
    }

    public boolean deleteGrupo(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }
        try {
            if (grupoRepository.existsById(id)) {
                grupoRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar grupo: " + e.getMessage());
        }
    }

    @Transactional
    public GrupoModel adicionarMembro(Long grupoId, Long perfilId) {
        GrupoModel grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado com id: " + grupoId));
        PerfilModel perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com id: " + perfilId));
        try {
            grupo.getMembros().add(perfil);
            return grupoRepository.save(grupo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar membro: " + e.getMessage());
        }
    }

    @Transactional
    public GrupoModel removerMembro(Long grupoId, Long perfilId) {
        GrupoModel grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado com id: " + grupoId));

        try {
            // cast para long primitivo para o removeIf funcionar corretamente
            grupo.getMembros().removeIf(p -> p.getId() == (long) perfilId);
            return grupoRepository.save(grupo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover membro: " + e.getMessage());
        }
    }

    @Transactional
    public GrupoModel adicionarFilme(Long grupoId, Long filmeId) {
        GrupoModel grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado com id: " + grupoId));
        FilmeModel filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com id: " + filmeId));
        try {
            grupo.getFilmes().add(filme);
            return grupoRepository.save(grupo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar filme ao grupo: " + e.getMessage());
        }
    }

    @Transactional
    public GrupoModel removerFilme(Long grupoId, Long filmeId) {
        GrupoModel grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado com id: " + grupoId));
        try {
            grupo.getFilmes().removeIf(f -> f.getId() == (long) filmeId);
            return grupoRepository.save(grupo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover filme do grupo: " + e.getMessage());
        }
    }
}