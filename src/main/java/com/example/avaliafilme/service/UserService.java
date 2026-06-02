package com.example.avaliafilme.service;

import org.springframework.beans.factory.annotation.Value;import org.springframework.context.annotation.Bean;

import com.example.avaliafilme.Model.TokenRecuperacaoModel;
import com.example.avaliafilme.Repository.TokenRecuperacaoRepository;
import com.example.avaliafilme.dto.RedefinirSenhaDTO;
import com.example.avaliafilme.dto.SolicitarRecuperacaoDTO;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import com.example.avaliafilme.Model.UserModel;
import com.example.avaliafilme.Repository.UserRepository;
import com.example.avaliafilme.dto.UserRequestDTO;
import com.example.avaliafilme.dto.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO addUser(UserRequestDTO user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail não pode ser vazio.");
        }
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 6 caracteres.");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
        throw new RuntimeException("Este e-mail já está cadastrado.");
        }

        try {
            UserModel newUser = new UserModel();
            newUser.setUsername(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setAge(user.getAge());

            UserModel userSaved = userRepository.save(newUser);

            return new UserResponseDTO(
                    userSaved.getId(),
                    userSaved.getUsername(),
                    userSaved.getEmail(),
                    userSaved.getDt_create()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    public List<UserResponseDTO> getAllUsers() {
        try {
            List<UserModel> userList = userRepository.findAll();
            List<UserResponseDTO> response = new ArrayList<>();

            for (UserModel u : userList) {
                response.add(new UserResponseDTO(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getDt_create()
                ));
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    public UserResponseDTO getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID informado é inválido.");
        }

        try {
            UserModel user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

            return new UserResponseDTO(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getDt_create()
            );
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO data) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID informado é inválido.");
        }
        if (data.getName() == null || data.getName().isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio.");
        }

        try {
            UserModel user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

            user.setUsername(data.getName());
            user.setEmail(data.getEmail());
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            user.setAge(data.getAge());

            UserModel userUpdated = userRepository.save(user);

            return new UserResponseDTO(
                    userUpdated.getId(),
                    userUpdated.getUsername(),
                    userUpdated.getEmail(),
                    userUpdated.getDt_create()
            );
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public boolean deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID informado é inválido.");
        }

        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário com id " + id + ": " + e.getMessage());
        }
    }

    public UserResponseDTO login(UserRequestDTO dto) {
        UserModel user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDt_create()
        );
     }

        private final TokenRecuperacaoRepository tokenRecuperacaoRepository;
        private final EmailService emailService;

        @Value("${app.frontend-url}")
        private String urlFrontend;

        @Transactional
        public void solicitarRecuperacao(SolicitarRecuperacaoDTO dto) {
            userRepository.findByEmail(dto.getEmail()).ifPresent(usuario -> {
                tokenRecuperacaoRepository.deleteByUsuarioAndUtilizadoEmIsNull(usuario);

                String codigo = UUID.randomUUID().toString();

                TokenRecuperacaoModel token = new TokenRecuperacaoModel();
                token.setUsuario(usuario);
                token.setCodigo(codigo);
                token.setValidoAte(LocalDateTime.now().plusMinutes(15));

                tokenRecuperacaoRepository.save(token);

                String link = urlFrontend + "/redefinir-senha?codigo=" + codigo;
                emailService.enviarEmailRecuperacao(usuario.getEmail(), link);
            });
        }

        @Transactional
        public void redefinirSenha(RedefinirSenhaDTO dto) {
            TokenRecuperacaoModel token = tokenRecuperacaoRepository
                    .findByCodigoAndUtilizadoEmIsNull(dto.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Código inválido ou já utilizado."));

            if (LocalDateTime.now().isAfter(token.getValidoAte())) {
                throw new RuntimeException("Este link expirou. Solicite um novo.");
            }

            UserModel usuario = token.getUsuario();
            usuario.setPassword(passwordEncoder.encode(dto.getNovaSenha()));
            userRepository.save(usuario);

            token.setUtilizadoEm(LocalDateTime.now());
            tokenRecuperacaoRepository.save(token);
        }
}