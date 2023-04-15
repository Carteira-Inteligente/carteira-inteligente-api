package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        if (StringUtils.isBlank(user.getName())) {
            errors.rejectValue("name", "user.name.mandatory", "Nome obrigatório");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            errors.rejectValue("email", "user.email.mandatory", "E-mail obrigatório");
        } else if (!isValidEmailAddress(user.getEmail())) {
            errors.rejectValue("email", "user.email.invalid", "E-mail inválido");
        }

    }

    public static boolean isValidEmailAddress(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
