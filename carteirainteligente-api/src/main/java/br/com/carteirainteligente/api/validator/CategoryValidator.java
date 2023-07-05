package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.repository.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    AutomaticCategoryRepository automaticCategoryRepository;

    @Autowired
    BudgetRepository budgetRepository;


    @Override
    public void validate(Object obj, Errors errors) {
        Category category = (Category) obj;
        if (category.getUser() == null) {
            errors.rejectValue("user", "category.user.mandatory", "Usuário obrigatório");
        } else if (category.getUser().getId() == null) {
            errors.rejectValue("user", "category.user.id.mandatory", "Id usuário obrigatório");
        } else if (userRepository.findById(category.getUser().getId()).orElse(null) == null) {
            errors.rejectValue("user", "category.user.not.found", "Usuário não encontrado");
        }
        if (StringUtils.isBlank(category.getDescription())) {
            errors.rejectValue("description", "category.description.mandatory", "Descrição obrigatória");
        }
        if (StringUtils.isBlank(category.getPathIcon())) {
            errors.rejectValue("icon", "category.icon.mandatory", "Ícone obrigatório");
        }
    }

    public void validateDelete(Long id, Errors errors) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            errors.rejectValue("category", "category.dependency", "Categoria não encontrada");
        }
        if (entryRepository.findByCategory(category) != null) {
            errors.rejectValue("id", "entry.dependency", "Categoria vinculada a um lançamento");
        }
        if (automaticCategoryRepository.findByCategory(category) != null) {
            errors.rejectValue("id", "entry.dependency", "Categoria vinculada a uma Categoria Automática");
        }
        if (budgetRepository.findByCategory(category) != null) {
            errors.rejectValue("id", "entry.dependency", "Categoria vinculada a um orçamento");
        }

    }

    public void validateSaveAndUpdate(Object obj, Long id, Errors errors) {
        Category category = (Category) obj;
        if (category.getId() != null) {
            category = categoryRepository.findById(id).orElse(null);
        }

        List<Category> categories = categoryRepository.findByDescription(category.getDescription());
        if (!categories.isEmpty()) {
            for (Category c : categories) {
                if (!c.getId().equals(category.getId())) {
                    errors.rejectValue("id", "category.exists", "Já existe uma categoria com esta descrição");
                }
            }
        }
    }
}