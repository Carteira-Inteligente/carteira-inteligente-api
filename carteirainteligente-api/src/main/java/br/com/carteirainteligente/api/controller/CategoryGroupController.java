package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.CategoryGroup;
import br.com.carteirainteligente.api.service.CategoryGroupService;
import br.com.carteirainteligente.api.validator.CategoryGroupValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-group")
public class CategoryGroupController {

    @Autowired
    private CategoryGroupService categoryGroupService;

    @Autowired
    private CategoryGroupValidator categoryGroupValidator;

    @GetMapping
    public ResponseEntity<List<CategoryGroup>> listCategoryGroups() {
        List<CategoryGroup> categoryGroups = categoryGroupService.listCategoryGroups();
        return categoryGroups.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoryGroups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryGroup> getCategoryGroup(@PathVariable Long id) {
        CategoryGroup categoryGroup = categoryGroupService.getCategoryGroup(id);
        return categoryGroup == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoryGroup);
    }

    @PostMapping
    public ResponseEntity<?> saveCategoryGroup(@RequestBody CategoryGroup categoryGroup, BindingResult result) {
        categoryGroupValidator.validate(categoryGroup, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        CategoryGroup savedCategoryGroup = categoryGroupService.saveCategoryGroup(categoryGroup);
        return ResponseEntity.ok(savedCategoryGroup);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryGroup(@PathVariable Long id, @RequestBody CategoryGroup categoryGroup, BindingResult result) {
        categoryGroupValidator.validate(categoryGroup, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        CategoryGroup updatedCategoryGroup = categoryGroupService.updateCategoryGroup(id, categoryGroup);
        return updatedCategoryGroup == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedCategoryGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryGroup> deleteCategoryGroup(@PathVariable Long id) {
        CategoryGroup deletedCategoryGroup = categoryGroupService.deleteCategoryGroup(id);
        return deletedCategoryGroup == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedCategoryGroup);
    }
}