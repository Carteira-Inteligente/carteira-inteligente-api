package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.service.CategoryService;
import br.com.carteirainteligente.api.validator.CategoryValidator;
import kong.unirest.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @GetMapping
    public ResponseEntity<List<Category>> listCategories() {
        List<Category> categories = categoryService.listCategories();
        return categories.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(categories) : ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody Category category, BindingResult result) {
        categoryValidator.validate(category, result);
        categoryValidator.validateSaveAndUpdate(category, null, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Category savedCategory = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody Category category, BindingResult result) {
        categoryValidator.validate(category, result);
        categoryValidator.validateSaveAndUpdate(category, id, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Category updatedCategory = categoryService.updateCategory(id, category);
        return updatedCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id/*, @RequestBody Category category, BindingResult result*/) {
        /*categoryValidator.validateDelete(id, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }*/
        Category deletedCategory = categoryService.deleteCategory(id);
        return deletedCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedCategory);
    }
}