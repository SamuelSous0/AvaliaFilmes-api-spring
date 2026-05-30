package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.PostPerfilModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostPerfilRepository extends JpaRepository<PostPerfilModel, Long >{
    List<PostPerfilModel> findByPerfilId(Long perfilId);
}