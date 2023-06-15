package br.com.carteirainteligente.api.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "budget", uniqueConstraints = @UniqueConstraint(columnNames = "id_category"))
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @OneToOne
    @JoinColumn(name = "id_category")
    @NotNull
    private Category category;
    @Column(name = "value", scale = 2, precision = 20)
    private BigDecimal value;
    private String description;
}