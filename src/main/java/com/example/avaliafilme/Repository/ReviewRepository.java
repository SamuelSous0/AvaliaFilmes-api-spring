package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, Long> {
    List<ReviewModel> findByFilmeId(Long filmeId);
    
    List<ReviewModel> findByPerfilId(Long perfilId);
}