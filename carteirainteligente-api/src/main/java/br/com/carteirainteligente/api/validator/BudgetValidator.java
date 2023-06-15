package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.Budget;
import br.com.carteirainteligente.api.repository.BudgetRepository;
import br.com.carteirainteligente.api.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BudgetValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Budget.class.equals(clazz);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Override
    public void validate(Object obj, Errors errors) {
        Budget budget = (Budget) obj;
        if (budget.getUser() == null) {
            errors.rejectValue("user", "budget.user.mandatory", "Usuário obrigatório");
        } else if (budget.getUser().getId() == null) {
            errors.rejectValue("user", "budget.user.id.mandatory", "Id usuário obrigatório");
        } else if (userRepository.findById(budget.getUser().getId()).orElse(null) == null) {
            errors.rejectValue("user", "budget.user.not.found", "Usuário não encontrado");
        }
        if (budget.getCategory() != null && budget.getCategory().getId() == null) {
            errors.rejectValue("category", "budget.category.id.mandatory", "Id categoria obrigatório");
        }
        if (StringUtils.isBlank(budget.getDescription())) {
            errors.rejectValue("description", "budget.description.mandatory", "Descrição obrigatória");
        }
        if (budget.getValue() == null) {
            errors.rejectValue("value", "budget.value.mandatory", "Valor obrigatório");
        } else if (budget.getValue().compareTo(0L)<0) {
            errors.rejectValue("value", "budget.value.negative", "Valor não pode ser negativo");
        }
    }
}
