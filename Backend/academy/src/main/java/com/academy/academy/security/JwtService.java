package com.academy.academy.security;

import com.academy.academy.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class JwtService {
    private final String SECRET = "yP7n4XQ9vL6bR2jS8wK1fZ3mT0aH5dE7GvN8pC2rU6yQ1xB9";

    private final Key secretKey = Keys.hmacShaKeyFor(SECRET.getBytes());

    private final long expiration = 60 * 1000 * 60 * 24;

    public String generateToken(User user)
    {
        //as we need to extract the role from this token to authorize the user on the
        // basis of there role we need the map to save the key value pair

        Map<String , Object> claims = new HashMap<>(); // this is the map that we will use to add the custom data in our token
        claims.put("role" , user.getRole());

        return  createToken(claims , user.getEmail());

    }

    //while creating the token we need to inject the role in it with the subject as a email
    public String createToken(Map<String , Object> claims , String subject)
    {
        return Jwts.builder() //this buider() starts building a new jwt and returns a builder object where you can set the subject,claims , expiration and signature
                .setClaims(claims) // to store the claims in the payload of the token
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey) //sign the jwt with the secret key . The signature is verified when the token is validated.
                .compact(); // finalaize and build the compact jwt string
    }

}
