package br.com.carteirainteligente.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
}
