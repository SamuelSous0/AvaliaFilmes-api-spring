package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.userModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<userModel, Long> {
}
