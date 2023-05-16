package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.PaymentType;
import br.com.carteirainteligente.api.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PaymentTypeValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PaymentType.class.equals(clazz);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(Object obj, Errors errors) {
        PaymentType paymentType = (PaymentType) obj;
        if (paymentType.getUser() == null) {
            errors.rejectValue("user", "paymentType.user.mandatory", "Usuário obrigatório");
        } else if (paymentType.getUser().getId() == null) {
            errors.rejectValue("user", "paymentType.user.id.mandatory", "Id usuário obrigatório");
        } else if (userRepository.findById(paymentType.getUser().getId()).orElse(null) == null) {
            errors.rejectValue("user", "paymentType.user.not.found", "Usuário não encontrado");
        }
        if (StringUtils.isBlank(paymentType.getDescription())) {
            errors.rejectValue("description", "category.description.mandatory", "Descrição obrigatória");
        }
    }
}
