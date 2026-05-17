package com.example.avaliafilme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String name;
    private String password;
    private String email;
    private int age;
}