package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AutomaticCategoryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AutomaticCategory.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        if (obj == null || !obj.equals("")) {
            errors.rejectValue("automatic.category", "automatic.category.input.mandatory", "Input obrigatório");
        }
    }
}
