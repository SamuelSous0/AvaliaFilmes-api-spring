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

    List<ReacaoModel> findByReviewId(Long reviewId);

    List<ReacaoModel> findByUserId(Long userId);

    Optional<ReacaoModel> findByUserIdAndReviewId(Long userId, Long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    @Query("SELECT AVG(r.nota) FROM ReacaoModel r WHERE r.review.id = :reviewId")
    Double calcularMediaByReviewId(@Param("reviewId") Long reviewId);

    void deleteByUserIdAndReviewId(Long userId, Long reviewId);
}