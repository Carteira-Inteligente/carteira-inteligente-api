package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.PaymentType;
import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.PaymentTypeRepository;
import br.com.carteirainteligente.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeService {

    @Autowired
    PaymentTypeRepository paymentTypeRepository;

    @Autowired
    UserRepository userRepository;

    public List<PaymentType> listPaymentTypes() {
        List<PaymentType> paymentTypes = paymentTypeRepository.findByDescription("Carteira");
        paymentTypes.addAll(paymentTypeRepository.findByDescriptionNotOrderByDescription("Carteira"));

        return paymentTypes;
    }

    public PaymentType getPaymentType(Long id) {
        return paymentTypeRepository.findById(id).orElse(null);
    }

    public PaymentType savePaymentType(PaymentType paymentType) {
        User user = userRepository.findById(paymentType.getUser().getId()).orElse(null);

        paymentType.setUser(user);
        return paymentTypeRepository.save(paymentType);
    }

    public PaymentType updatePaymentType(Long id, PaymentType paymentType) {
        PaymentType existingPaymentType = paymentTypeRepository.findById(id).orElse(null);
        User user = userRepository.findById(paymentType.getUser().getId()).orElse(null);

        if(existingPaymentType != null) {

            existingPaymentType.setUser(user);
            existingPaymentType.setDescription(paymentType.getDescription());
            existingPaymentType.setType(paymentType.getType());

            paymentTypeRepository.save(existingPaymentType);
            return existingPaymentType;
        }

        return null;
    }

    public PaymentType deletePaymentType(Long id) {
        PaymentType existingPaymentType = paymentTypeRepository.findById(id).orElse(null);

        if(existingPaymentType != null) {
            paymentTypeRepository.deleteById(id);
            return existingPaymentType;
        }

        return null;
    }
}
