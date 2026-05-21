package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.FilmeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<FilmeModel, Long> {

}