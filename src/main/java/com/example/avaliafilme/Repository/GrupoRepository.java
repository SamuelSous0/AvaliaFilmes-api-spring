package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.GrupoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<GrupoModel, Long> {

    List<GrupoModel> findByCriadorId(Long criadorId);

    List<GrupoModel> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT g FROM GrupoModel g JOIN g.membros m WHERE m.id = :perfilId")
    List<GrupoModel> findByMembroId(@Param("perfilId") Long perfilId);
}