package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository  extends JpaRepository<Budget, Long> {
}
