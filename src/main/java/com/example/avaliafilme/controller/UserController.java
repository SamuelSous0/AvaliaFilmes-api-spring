package com.example.avaliafilme.controller;

import com.example.avaliafilme.dto.UserRequestDTO;
import com.example.avaliafilme.dto.UserResponseDTO;
import com.example.avaliafilme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

        @Autowired
        UserService userService;

        @GetMapping("/allUsers")
        public List<UserResponseDTO> getAllUsers() {

            return userService.getAllUsers();
        }

        @PostMapping("/add")
        public UserResponseDTO newUser(@RequestBody UserRequestDTO user) {

            return userService.addUser(user);
        }

        @GetMapping("/user/{id}")
        public UserResponseDTO getUser(@PathVariable Long id) {

            return userService.getUserById(id);
        }

        @PutMapping("/update/{id}")
        public UserResponseDTO updateUser(
                @RequestBody UserRequestDTO user,
                @PathVariable Long id) {

            return userService.updateUser(id, user);
        }

        @DeleteMapping("/user/{id}")
        public ResponseEntity.BodyBuilder deleteUser(@PathVariable Long id) {
            boolean deleted = userService.deleteUser(id);
            if (deleted)
                return ResponseEntity.status(HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.NOT_FOUND);
        }

    }
