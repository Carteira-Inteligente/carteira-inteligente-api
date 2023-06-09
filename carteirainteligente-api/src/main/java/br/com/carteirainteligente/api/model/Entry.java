package br.com.carteirainteligente.api.model;

import br.com.carteirainteligente.api.enums.PeriodEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "entry")
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_payment_type")
    private PaymentType paymentType;
    private String description;
    private PeriodEnum period;
    @Column(name = "paidvalue", scale = 2, precision = 20)
    private BigDecimal paidValue;
    private Date paidDate;
    private Boolean paid;
    private Date dueDate;
}
