package br.com.carteirainteligente.api.repository;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomaticCategoryRepository extends JpaRepository<AutomaticCategory, Long> {

    AutomaticCategory findTop1ByInput(String input);
}
