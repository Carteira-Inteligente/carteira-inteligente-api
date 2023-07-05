package br.com.carteirainteligente.api.validator;

import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.*;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BudgetRepository budgetRepository;

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

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

    public void validateSave(Object obj, Errors errors) {
        User user = (User) obj;
        List<User> users = userRepository.findByEmail(user.getEmail());

        if (!users.isEmpty()) {
            errors.rejectValue("id", "user.exists", "Já existe um usuário com este e-mail");
        }
    }

    public void validateUpdate(Object obj, Long id, Errors errors) {
        User newUser = (User) obj;
        User oldUser = userRepository.findById(id).orElse(null);

        if (oldUser != null) {
            List<User> users = userRepository.findByEmail(newUser.getEmail());
            if (!users.isEmpty()) {
                for (User u : users) {
                    if (!u.getId().equals(oldUser.getId())) {
                        errors.rejectValue("id", "user.exists", "Já existe um usuário com este e-mail");
                    }
                }
            }
        }
    }

    public void validateDelete(Long id, Errors errors) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            errors.rejectValue("user", "user.dependency", "Usuário não encontrado");
        }
        if (!entryRepository.findByUser(user).isEmpty()) {
            errors.rejectValue("id", "user.dependency", "Usuário vinculado a um lançamento");
        }
        if (categoryRepository.findByUser(user) != null) {
            errors.rejectValue("id", "user.dependency", "Usuário vinculado a uma categoria");
        }
        if (budgetRepository.findByUser(user) != null) {
            errors.rejectValue("id", "user.dependency", "Usuário vinculado a um orçamento");
        }
        if (paymentTypeRepository.findByUser(user) != null) {
            errors.rejectValue("id", "user.dependency", "Usuário vinculado a um tipo de pagamento");
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
