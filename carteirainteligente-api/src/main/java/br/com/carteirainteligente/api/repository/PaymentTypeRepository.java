package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.enums.TypeEnum;
import br.com.carteirainteligente.api.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    PaymentType findTop1ByType(TypeEnum type);
}
