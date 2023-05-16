package br.com.carteirainteligente.api.model;

import br.com.carteirainteligente.api.enums.TypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payment_type")
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    private TypeEnum type;
    private String description;
}