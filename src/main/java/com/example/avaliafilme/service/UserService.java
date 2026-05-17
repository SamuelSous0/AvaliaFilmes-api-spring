package com.example.avaliafilme.service;

import com.example.avaliafilme.Model.userModel;
import com.example.avaliafilme.Repository.userRepository;
import com.example.avaliafilme.dto.UserRequestDTO;
import com.example.avaliafilme.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    userRepository userRepository;

    public UserResponseDTO addUser(UserRequestDTO user) {

        userModel newUser = new userModel();

        newUser.setUsername(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setAge(user.getAge());

        userModel userSaved = userRepository.save(newUser);

        UserResponseDTO response = new UserResponseDTO(
                userSaved.getId(),
                userSaved.getUsername(),
                userSaved.getEmail(),
                userSaved.getDt_create()
        );

        return response;
    }

    public List<UserResponseDTO> getAllUsers() {

        List<userModel> userList = userRepository.findAll();

        List<UserResponseDTO> response = new ArrayList<UserResponseDTO>();

        for (userModel user : userList) {

            response.add(
                    new UserResponseDTO(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getDt_create()
                    )
            );
        }

        return response;
    }

    public UserResponseDTO getUserById(Long id) {

        userModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDt_create()
        );

        return response;
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO data) {

        userModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));

        user.setUsername(data.getName());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        user.setAge(data.getAge());

        userModel userUpdated = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO(
                userUpdated.getId(),
                userUpdated.getUsername(),
                userUpdated.getEmail(),
                userUpdated.getDt_create()
        );

        return response;
    }

    public boolean deleteUser(Long id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}