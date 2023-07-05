package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import br.com.carteirainteligente.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomaticCategoryRepository extends JpaRepository<AutomaticCategory, Long> {
    AutomaticCategory findTop1ByInput(String input);

    List<AutomaticCategory> findByCategory(Category category);
}
