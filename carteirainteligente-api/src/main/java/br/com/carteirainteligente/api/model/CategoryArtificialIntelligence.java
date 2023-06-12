package br.com.carteirainteligente.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category_artificial_intelligence")
public class CategoryArtificialIntelligence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    private String question;
    private String answer;
}
