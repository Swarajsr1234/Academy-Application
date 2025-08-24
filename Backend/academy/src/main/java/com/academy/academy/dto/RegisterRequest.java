package com.academy.academy.dto;

import com.academy.academy.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
