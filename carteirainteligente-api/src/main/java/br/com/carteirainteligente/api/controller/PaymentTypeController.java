package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.model.PaymentType;
import br.com.carteirainteligente.api.service.CategoryService;
import br.com.carteirainteligente.api.service.PaymentTypeService;
import br.com.carteirainteligente.api.validator.CategoryValidator;
import br.com.carteirainteligente.api.validator.PaymentTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment-type")
public class PaymentTypeController {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private PaymentTypeValidator paymentTypeValidator;

    @GetMapping
    public ResponseEntity<List<PaymentType>> listPaymentTypes() {
        List<PaymentType> categories = paymentTypeService.listPaymentTypes();
        return categories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentType> getPaymentType(@PathVariable Long id) {
        PaymentType paymentType = paymentTypeService.getPaymentType(id);
        return paymentType == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(paymentType);
    }

    @PostMapping
    public ResponseEntity<?> savePaymentType(@RequestBody PaymentType paymentType, BindingResult result) {
        paymentTypeValidator.validate(paymentType, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        PaymentType savedPaymentType = paymentTypeService.savePaymentType(paymentType);
        return ResponseEntity.ok(savedPaymentType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaymentType(@PathVariable Long id, @RequestBody PaymentType paymentType, BindingResult result) {
        paymentTypeValidator.validate(paymentType, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        PaymentType updatedPaymentType = paymentTypeService.updatePaymentType(id, paymentType);
        return updatedPaymentType == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedPaymentType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentType> deletePaymentType(@PathVariable Long id) {
        PaymentType deletedPaymentType = paymentTypeService.deletePaymentType(id);
        return deletedPaymentType == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedPaymentType);
    }
}
