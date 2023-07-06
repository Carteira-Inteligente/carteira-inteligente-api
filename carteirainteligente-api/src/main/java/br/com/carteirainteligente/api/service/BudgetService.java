package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.Budget;
import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.BudgetRepository;
import br.com.carteirainteligente.api.repository.CategoryRepository;
import br.com.carteirainteligente.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Budget> listBudgets() {
        return budgetRepository.findAllByOrderByDescriptionAsc();
    }

    public Budget getBudget(Long id) {
        return budgetRepository.findById(id).orElse(null);
    }

    public Budget saveBudget(Budget budget) {
        User user = userRepository.findById(budget.getUser().getId()).orElse(null);
        Category category = categoryRepository.findById(budget.getCategory().getId()).orElse(null);

        budget.setUser(user);
        budget.setCategory(category);
        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Long id, Budget budget) {
        Budget existingBudget = budgetRepository.findById(id).orElse(null);
        User user = userRepository.findById(budget.getUser().getId()).orElse(null);
        Category category = null;
        if (budget.getCategory() != null) {
            category = categoryRepository.findById(budget.getCategory().getId()).orElse(null);
        }

        if(existingBudget != null) {

            existingBudget.setUser(user);
            existingBudget.setCategory(category);
            existingBudget.setDescription(budget.getDescription());
            existingBudget.setValue(budget.getValue());

            budgetRepository.save(existingBudget);
            return existingBudget;
        }

        return null;
    }

    public Budget deleteBudget(Long id) {
        Budget existingBudget = budgetRepository.findById(id).orElse(null);

        if(existingBudget != null) {
            budgetRepository.deleteById(id);
            return existingBudget;
        }

        return null;
    }
}
