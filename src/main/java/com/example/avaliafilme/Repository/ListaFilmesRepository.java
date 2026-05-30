package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.ListaFilmesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListaFilmesRepository extends JpaRepository<ListaFilmesModel, Long> {

    List<ListaFilmesRepository> findByCriadorId(Long criadorId);
}
