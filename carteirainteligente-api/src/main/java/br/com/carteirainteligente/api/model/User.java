package br.com.carteirainteligente.api.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class User {
    private Long id;
    private String username;
    private String password;
}
