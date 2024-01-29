package com.oomool.api.domain.user.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.oomool.api.domain.auth.entity.SocialLogin;
import com.oomool.api.domain.player.entity.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonBackReference // 순환참조 방지
    private List<SocialLogin> socialLoginList;

    @OneToMany(mappedBy = "user")
    private List<Player> playerList;

}
