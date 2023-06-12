package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import br.com.carteirainteligente.api.service.AutomaticCategoryService;
import br.com.carteirainteligente.api.validator.AutomaticCategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/automatic-category")
public class AutomaticCategoryController {

    @Autowired
    AutomaticCategoryService automaticCategoryService;

    @Autowired
    AutomaticCategoryValidator automaticCategoryValidator;

    @GetMapping
    public ResponseEntity<?> getAutomaticCategory(@RequestBody String input, BindingResult result) {
        automaticCategoryValidator.validate(input, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        AutomaticCategory automaticCategory = automaticCategoryService.getAutomaticCategory(input);
        return automaticCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(automaticCategory);
    }
}
