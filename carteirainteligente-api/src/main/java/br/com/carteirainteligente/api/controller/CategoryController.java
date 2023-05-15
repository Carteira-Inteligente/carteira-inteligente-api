package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.service.CategoryService;
import br.com.carteirainteligente.api.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-group")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryValidator categoryValidator;

    @GetMapping
    public ResponseEntity<List<Category>> listCategoryGroups() {
        List<Category> categories = categoryService.listCategoryGroups();
        return categories.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryGroup(@PathVariable Long id) {
        Category category = categoryService.getCategoryGroup(id);
        return category == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<?> saveCategoryGroup(@RequestBody Category category, BindingResult result) {
        categoryValidator.validate(category, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Category savedCategory = categoryService.saveCategoryGroup(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryGroup(@PathVariable Long id, @RequestBody Category category, BindingResult result) {
        categoryValidator.validate(category, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Category updatedCategory = categoryService.updateCategoryGroup(id, category);
        return updatedCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategoryGroup(@PathVariable Long id) {
        Category deletedCategory = categoryService.deleteCategoryGroup(id);
        return deletedCategory == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedCategory);
    }
}