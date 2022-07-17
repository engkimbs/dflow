package com.lucorda.dflow.user.command.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String userName;

    String password;

    String email;

    @Enumerated(EnumType.STRING)
    Role role;

    public enum Role {
        ADMIN, USER
    }
}
