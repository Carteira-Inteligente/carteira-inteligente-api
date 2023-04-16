package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.CategoryGroup;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CategoryGroupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CategoryGroup.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CategoryGroup categoryGroup = (CategoryGroup) obj;
        if (categoryGroup.getUser() == null) {
            errors.rejectValue("user", "category.group.user.mandatory", "Usuário obrigatório");
        }
        if (StringUtils.isBlank(categoryGroup.getDescription())) {
            errors.rejectValue("description", "category.group.description.mandatory", "Descrição obrigatória");
        }
        if (StringUtils.isBlank(categoryGroup.getIcon())) {
            errors.rejectValue("icon", "category.group.icon.mandatory", "Ícone obrigatório");
        }
    }
}