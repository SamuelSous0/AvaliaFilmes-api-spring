package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Model.ListaFilmesModel;
import com.example.avaliafilme.Model.PerfilModel;
import com.example.avaliafilme.Model.UserModel;
import com.example.avaliafilme.Repository.FilmeRepository;
import com.example.avaliafilme.Repository.ListaFilmesRepository;
import com.example.avaliafilme.Repository.PerfilRepository;
import com.example.avaliafilme.Repository.UserRepository;
import com.example.avaliafilme.dto.AdicionarColaboradorDTO;
import com.example.avaliafilme.dto.AdicionarFilmeDTO;
import com.example.avaliafilme.dto.ListaRequestDTO;
import com.example.avaliafilme.dto.ListaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaFilmesService {

    private final ListaFilmesRepository listaRepository;
    private final UserRepository userRepository;
    private final FilmeRepository filmeRepository;
    private final PerfilRepository perfilRepository;

    public ListaResponseDTO criar(ListaRequestDTO dto) {
        UserModel criador = userRepository.findById(dto.getCriadorId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        ListaFilmesModel lista = ListaFilmesModel.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .publica(dto.isPublica())
                .criador(criador)
                .filmes(new ArrayList<>())
                .colaboradores(new ArrayList<>())
                .build();

        return toDTO(listaRepository.save(lista));
    }

    public List<ListaResponseDTO> buscarTodas() {
        return listaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public ListaResponseDTO buscarPorId(Long id) {
        ListaFilmesModel lista = listaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));
        return toDTO(lista);
    }

    public ListaResponseDTO atualizar(Long id, ListaRequestDTO dto) {
        ListaFilmesModel lista = listaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        lista.setNome(dto.getNome());
        lista.setDescricao(dto.getDescricao());
        lista.setPublica(dto.isPublica());

        return toDTO(listaRepository.save(lista));
    }

    public void deletar(Long id) {
        listaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));
        listaRepository.deleteById(id);
    }

    public ListaResponseDTO adicionarFilme(Long listaId, AdicionarFilmeDTO dto) {
        ListaFilmesModel lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        FilmeModel filme = filmeRepository.findById(dto.getFilmeId())
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        if (lista.getFilmes().contains(filme)) {
            throw new RuntimeException("Filme já está na lista");
        }

        lista.getFilmes().add(filme);
        return toDTO(listaRepository.save(lista));
    }

    private ListaResponseDTO toDTO(ListaFilmesModel lista) {
        return ListaResponseDTO.builder()
                .id(lista.getId())
                .nome(lista.getNome())
                .descricao(lista.getDescricao())
                .publica(lista.isPublica())
                .criadorUsername(lista.getCriador().getUsername())
                .quantidadeFilmes(lista.getFilmes().size())
                .colaboradores(lista.getColaboradores()
                        .stream()
                        .map(perfil -> perfil.getUser().getUsername())
                        .toList())
                .criadoEm(lista.getDt_create())
                .build();
    }

    public ListaResponseDTO adicionarColaborador(Long listaId, AdicionarColaboradorDTO dto) {
        ListaFilmesModel lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        PerfilModel perfil = perfilRepository.findById(dto.getPerfilId())
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        if (lista.getColaboradores().contains(perfil)) {
            throw new RuntimeException("Colaborador já está na lista");
        }

        lista.getColaboradores().add(perfil);
        return toDTO(listaRepository.save(lista));
    }

    public List<String> buscarFilmesDaLista(Long listaId) {
        ListaFilmesModel lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        return lista.getFilmes()
                .stream()
                .map(FilmeModel::getTitulo)
                .toList();
    }

    public ListaResponseDTO removerFilme(Long listaId, Long filmeId) {
        ListaFilmesModel lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        FilmeModel filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado"));

        if (!lista.getFilmes().contains(filme)) {
            throw new RuntimeException("Filme não está na lista");
        }

        lista.getFilmes().remove(filme);
        return toDTO(listaRepository.save(lista));
    }

    public ListaResponseDTO removerColaborador(Long listaId, Long perfilId) {
        ListaFilmesModel lista = listaRepository.findById(listaId)
                .orElseThrow(() -> new RuntimeException("Lista não encontrada"));

        PerfilModel perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        if (!lista.getColaboradores().contains(perfil)) {
            throw new RuntimeException("Colaborador não está na lista");
        }

        lista.getColaboradores().remove(perfil);
        return toDTO(listaRepository.save(lista));
    }
}
