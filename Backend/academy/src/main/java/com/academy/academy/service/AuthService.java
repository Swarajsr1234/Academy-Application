package com.academy.academy.service;

import com.academy.academy.dto.LoginRequest;
import com.academy.academy.dto.LoginResponse;
import com.academy.academy.dto.RegisterRequest;
import com.academy.academy.dto.RegisterResponse;
import com.academy.academy.model.Role;
import com.academy.academy.model.User;
import com.academy.academy.repository.UserRepository;
import com.academy.academy.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    //now we need to send token right so we need to create token first so our token generation logic
    // will be inside the jwtservice once we will build that generate token logic inside that jwtservice
    // then we will come back here

    private final JwtService jwtService;


    public RegisterResponse register(RegisterRequest request)
    {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new RegisterResponse("User Register Successfully.." , token);
    }

    public LoginResponse login(LoginRequest request)
    {

        // here we have the authentication manager which will cross checkand becrypt the password
        // then the matching the password all those things will be done by authentication manager

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        return new LoginResponse("Login Success" , token);
    }
}
