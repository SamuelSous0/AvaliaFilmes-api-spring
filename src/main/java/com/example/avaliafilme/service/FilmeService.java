package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.FilmeModel;
import com.example.avaliafilme.Repository.FilmeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {

    @Autowired
    FilmeRepository filmeRepository;

    public FilmeModel addFilme(FilmeModel filme) {

        if (filme.getTitulo() == null || filme.getTitulo().isBlank()) {
            throw new IllegalArgumentException("O título do filme não pode ser vazio.");
        }

        if (filme.getGenero() == null || filme.getGenero().isBlank()) {
            throw new IllegalArgumentException("O gênero do filme não pode ser vazio.");
        }

        if (filme.getDiretor() == null || filme.getDiretor().isBlank()) {
            throw new IllegalArgumentException("O diretor do filme não pode ser vazio.");
        }

        try {

            return filmeRepository.save(filme);

        } catch (Exception e) {

            throw new RuntimeException("Erro ao salvar filme: " + e.getMessage());
        }
    }

    public List<FilmeModel> getAllFilmes() {

        try {

            return filmeRepository.findAll();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao buscar filmes: " + e.getMessage());
        }
    }

    public FilmeModel getFilmeById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        try {

            return filmeRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Filme não encontrado com id: " + id));

        } catch (RuntimeException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException("Erro ao buscar filme: " + e.getMessage());
        }
    }

    public FilmeModel updateFilme(Long id, FilmeModel data) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        try {

            FilmeModel filme = filmeRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Filme não encontrado com id: " + id));

            filme.setTitulo(data.getTitulo());
            filme.setGenero(data.getGenero());
            filme.setAnoLancamento(data.getAnoLancamento());
            filme.setDiretor(data.getDiretor());
            filme.setDescricao(data.getDescricao());

            return filmeRepository.save(filme);

        } catch (RuntimeException e) {

            throw e;

        } catch (Exception e) {

            throw new RuntimeException("Erro ao atualizar filme: " + e.getMessage());
        }
    }

    public boolean deleteFilme(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        try {

            if (filmeRepository.existsById(id)) {

                filmeRepository.deleteById(id);

                return true;
            }

            return false;

        } catch (Exception e) {

            throw new RuntimeException("Erro ao deletar filme: " + e.getMessage());
        }
    }
}