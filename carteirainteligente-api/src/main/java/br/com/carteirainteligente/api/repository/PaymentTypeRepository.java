package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.enums.TypeEnum;
import br.com.carteirainteligente.api.model.PaymentType;
import br.com.carteirainteligente.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    PaymentType findTop1ByType(TypeEnum type);

    PaymentType findByUser(User user);

    List<PaymentType> findByDescriptionNotOrderByDescription(String description);

    List<PaymentType> findByDescription(String description);
}
