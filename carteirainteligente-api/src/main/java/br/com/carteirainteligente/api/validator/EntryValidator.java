package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.Entry;
import br.com.carteirainteligente.api.model.Entry;
import br.com.carteirainteligente.api.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EntryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Entry.class.equals(clazz);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void validate(Object obj, Errors errors) {
        Entry entry = (Entry) obj;
        if (entry.getUser() == null) {
            errors.rejectValue("user", "entry.user.mandatory", "Usuário obrigatório");
        } else if (entry.getUser().getId() == null) {
            errors.rejectValue("user", "entry.user.id.mandatory", "Id usuário obrigatório");
        } else if (userRepository.findById(entry.getUser().getId()).orElse(null) == null) {
            errors.rejectValue("user", "entry.user.not.found", "Usuário não encontrado");
        }
        if (entry.getCategory() != null && entry.getCategory().getId() == null) {
            errors.rejectValue("category", "entry.category.id.mandatory", "Id categoria obrigatório");
        }
        if (entry.getPaymentType() != null && entry.getPaymentType().getId() == null) {
            errors.rejectValue("paymentType", "payment.type.category.id.mandatory", "Id tipo de pagamento obrigatório");
        }
        if (StringUtils.isBlank(entry.getDescription())) {
            errors.rejectValue("description", "entry.description.mandatory", "Descrição obrigatória");
        }
        if (entry.getPaidValue() == null) {
            errors.rejectValue("paidValue", "entry.paid.value.mandatory", "Valor obrigatório");
        } else if (entry.getPaidValue().compareTo(0L)<0) {
            errors.rejectValue("paidValue", "entry.paid.value.negative", "Valor não pode ser negativo");
        }
    }
}
