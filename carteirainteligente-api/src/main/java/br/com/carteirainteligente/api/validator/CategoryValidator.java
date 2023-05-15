package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Category.class.equals(clazz);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(Object obj, Errors errors) {
        Category category = (Category) obj;
        if (category.getUser() == null) {
            errors.rejectValue("user", "category.group.user.mandatory", "Usuário obrigatório");
        } else if (category.getUser().getId() == null) {
            errors.rejectValue("user", "category.group.user.id.mandatory", "Id usuário obrigatório");
        } else if (userRepository.findById(category.getUser().getId()).orElse(null) == null) {
            errors.rejectValue("user", "category.group.user.not.found", "Usuário não encontrado");
        }
        if (StringUtils.isBlank(category.getDescription())) {
            errors.rejectValue("description", "category.group.description.mandatory", "Descrição obrigatória");
        }
        if (StringUtils.isBlank(category.getIcon())) {
            errors.rejectValue("icon", "category.group.icon.mandatory", "Ícone obrigatório");
        }
    }
}