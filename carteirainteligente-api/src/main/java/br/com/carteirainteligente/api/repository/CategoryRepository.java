package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findTop1ByDescription(String description);

    List<Category> findByIsDefault(Boolean isDefault);

    List<Category> findByDescription(String description);

}
