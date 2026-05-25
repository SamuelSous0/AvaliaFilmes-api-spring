package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.UserModel;
import com.example.avaliafilme.Repository.UserRepository;
import com.example.avaliafilme.dto.UserRequestDTO;
import com.example.avaliafilme.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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

        try {
            UserModel newUser = new UserModel();
            newUser.setUsername(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
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
            user.setPassword(data.getPassword());
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
}