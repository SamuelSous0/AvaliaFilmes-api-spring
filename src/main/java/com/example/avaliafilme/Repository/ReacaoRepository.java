package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.ReacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReacaoRepository extends JpaRepository<ReacaoModel, Long> {

    List<ReacaoModel> findByReview_Id(Long reviewId);

<<<<<<< HEAD
    List<ReacaoModel> findByPerfil_Id(Long perfilId);

    Optional<ReacaoModel> findByPerfil_IdAndReview_Id(Long perfilId, Long reviewId);

    boolean existsByPerfil_IdAndReview_Id(Long perfilId, Long reviewId);
=======
    List<ReacaoModel> findByPerfil_Id(Long perfilId);

    Optional<ReacaoModel> findByPerfil_IdAndReview_Id(Long perfilId, Long reviewId);

    boolean existsByPerfil_IdAndReview_Id(Long perfilId, Long reviewId);
>>>>>>> main

    @Query("SELECT AVG(r.nota) FROM ReacaoModel r WHERE r.review.id = :reviewId")
    Double calcularMediaByReviewId(@Param("reviewId") Long reviewId);

<<<<<<< HEAD
    void deleteByPerfil_IdAndReview_Id(Long perfilId, Long reviewId);
=======
    void deleteByUser_IdAndReview_Id(Long userId, Long reviewId);
>>>>>>> main
}