package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.PerfilModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilModel, Long> {

}
