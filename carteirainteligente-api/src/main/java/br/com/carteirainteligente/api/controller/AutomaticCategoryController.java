package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.AutomaticCategory;
import br.com.carteirainteligente.api.service.AutomaticCategoryService;
import br.com.carteirainteligente.api.validator.AutomaticCategoryValidator;
import kong.unirest.HttpStatus;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/automatic-category")
public class AutomaticCategoryController {

    @Autowired
    AutomaticCategoryService automaticCategoryService;

    @Autowired
    AutomaticCategoryValidator automaticCategoryValidator;

    @PostMapping
    public ResponseEntity<?> saveAutomaticCategory(@RequestBody AutomaticCategory automaticCategory) {
        AutomaticCategory savedAutomaticCategory = automaticCategoryService.saveAutomaticCategory(automaticCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAutomaticCategory);
    }

    @GetMapping
    public ResponseEntity<?> getAutomaticCategory(@RequestBody String inputJson, BindingResult result) {
        AutomaticCategory automaticCategoryInput = new AutomaticCategory();
        automaticCategoryInput.setInput(inputJson);

        automaticCategoryValidator.validate(automaticCategoryInput, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        JSONObject jsonObject = new JSONObject(inputJson);
        String input = jsonObject.getString("inputJson");

        AutomaticCategory automaticCategory = automaticCategoryService.getAutomaticCategory(input);
        return automaticCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(automaticCategory);
    }
}
