package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.CategoryGroup;
import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryGroupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryGroup.class.equals(clazz);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(Object obj, Errors errors) {
        CategoryGroup categoryGroup = (CategoryGroup) obj;
        if (categoryGroup.getUser() == null) {
            errors.rejectValue("user", "category.group.user.mandatory", "Usuário obrigatório");
        } else if (categoryGroup.getUser().getId() == null) {
            errors.rejectValue("user", "category.group.user.id.mandatory", "Id usuário obrigatório");
        } else if (userRepository.findById(categoryGroup.getUser().getId()).orElse(null) == null) {
            errors.rejectValue("user", "category.group.user.not.found", "Usuário não encontrado");
        }
        if (StringUtils.isBlank(categoryGroup.getDescription())) {
            errors.rejectValue("description", "category.group.description.mandatory", "Descrição obrigatória");
        }
        if (StringUtils.isBlank(categoryGroup.getIcon())) {
            errors.rejectValue("icon", "category.group.icon.mandatory", "Ícone obrigatório");
        }
    }
}