package com.example.avaliafilme.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.avaliafilme.Model.FavoritoModel;

@Repository
public interface FavoritoRepository extends JpaRepository<FavoritoModel, Long>{

    List<FavoritoModel> findByUsuarioId(Long userId);

    boolean existsByUsuarioIdAndFilmeId(
            Long userId,
            Long filmeId
    );

}
    
