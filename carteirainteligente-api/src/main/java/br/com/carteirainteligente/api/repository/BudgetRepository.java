package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.model.Budget;
import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUser(User user);

    List<Budget> findByCategory(Category category);

    List<Budget> findAllByOrderByDescriptionAsc();
}
