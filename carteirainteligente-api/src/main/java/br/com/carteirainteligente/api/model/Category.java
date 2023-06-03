package br.com.carteirainteligente.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @OneToOne
    @JoinColumn(name = "id_budget")
    private Budget budget;
    private String description;
    private String icon;
    private String pathIcon;
    private String iconColor;
    private String backgroundColor;
}
