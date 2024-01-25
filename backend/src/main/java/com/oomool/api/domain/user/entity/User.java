package com.oomool.api.domain.user.entity;

import java.util.List;

import com.oomool.api.domain.auth.entity.SocialLogin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    private List<SocialLogin> socialLoginList;
}
