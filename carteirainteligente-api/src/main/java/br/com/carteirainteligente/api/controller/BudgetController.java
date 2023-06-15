package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.Budget;
import br.com.carteirainteligente.api.service.BudgetService;
import br.com.carteirainteligente.api.validator.BudgetValidator;
import kong.unirest.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private BudgetValidator budgetValidator;

    @GetMapping
    public ResponseEntity<List<Budget>> listBudgets() {
        List<Budget> budgets = budgetService.listBudgets();
        return budgets.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(budgets) : ResponseEntity.ok(budgets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Budget> getBudget(@PathVariable Long id) {
        Budget budget = budgetService.getBudget(id);
        return budget == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(budget);
    }

    @PostMapping
    public ResponseEntity<?> saveBudget(@RequestBody Budget budget, BindingResult result) {
        budgetValidator.validate(budget, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Budget savedBudget = budgetService.saveBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBudget);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBudget(@PathVariable Long id, @RequestBody Budget budget, BindingResult result) {
        budgetValidator.validate(budget, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Budget updatedBudget = budgetService.updateBudget(id, budget);
        return updatedBudget == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedBudget);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Budget> deleteBudget(@PathVariable Long id) {
        Budget deletedBudget = budgetService.deleteBudget(id);
        return deletedBudget == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedBudget);
    }
}
