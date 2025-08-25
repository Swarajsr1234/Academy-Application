package com.academy.academy.service;

import com.academy.academy.dto.RegisterRequest;
import com.academy.academy.dto.RegisterResponse;
import com.academy.academy.model.User;
import com.academy.academy.repository.UserRepository;
import com.academy.academy.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    //now we need to send token right so we need to create token first so our token generation login
    // will be inside the jwtservice once we will build that generate token logic inside that jwtservice
    // then we will come back here

    private final JwtService jwtService;


    public RegisterResponse register(RegisterRequest request)
    {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return new RegisterResponse("User Register Successfully.." , token);
    }


}
