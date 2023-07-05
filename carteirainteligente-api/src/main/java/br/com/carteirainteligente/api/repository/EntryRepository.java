package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.model.Entry;
import br.com.carteirainteligente.api.model.PaymentType;
import br.com.carteirainteligente.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findByUser(User user);

    List<Entry>  findByPaymentType(PaymentType paymentType);

    List<Entry>  findByCategory(Category category);

}
