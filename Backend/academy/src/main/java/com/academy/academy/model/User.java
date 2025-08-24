package com.academy.academy.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true , nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(name = "created_at" , updatable = false)
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate()
    {
    this.createdAt = LocalDate.now();
    }

}
