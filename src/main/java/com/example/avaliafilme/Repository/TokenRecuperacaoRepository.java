package com.example.avaliafilme.Repository;

import com.example.avaliafilme.Model.TokenRecuperacaoModel;
import com.example.avaliafilme.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenRecuperacaoRepository extends JpaRepository<TokenRecuperacaoModel, Long> {
    Optional<TokenRecuperacaoModel> findByCodigoAndUtilizadoEmIsNull(String codigo);
    void deleteByUsuarioAndUtilizadoEmIsNull(UserModel usuario);
}