package com.academy.academy.service;

import com.academy.academy.model.User;
import com.academy.academy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


// Login api caused Stackoverflow due to circular proxy calls
//Spring Security's AuthenticationManager was invoking itself because no proper UserDetailsService was configured.
//Implemented CustomUserDetailsService to load users from DB and provide authorities.

//Now my circular dependency is resolved and Login and registration is working properly

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not Found..."));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
