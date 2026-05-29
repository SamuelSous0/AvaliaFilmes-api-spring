package com.example.avaliafilme.Repository;
import com.example.avaliafilme.Model.PostPerfilModel;
import org.springframework.data.jpa.repositoy.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPerfilRepository extends JpaRepository<PostPerfilModel, Long >{
    List<PostPerfilModel> findByPerfilId(Long perfilId);
}