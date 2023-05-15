package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.Category;
import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.repository.CategoryRepository;
import br.com.carteirainteligente.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    public List<Category> listCategoryGroups() {
        return categoryRepository.findAll();
    }

    public Category getCategoryGroup(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category saveCategoryGroup(Category category) {
        User user = userRepository.findById(category.getUser().getId()).orElse(null);

        category.setUser(user);
        return categoryRepository.save(category);
    }

    public Category updateCategoryGroup(Long id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        User user = userRepository.findById(category.getUser().getId()).orElse(null);

        if(existingCategory != null) {

            existingCategory.setUser(user);
            existingCategory.setDescription(category.getDescription());
            existingCategory.setIcon(category.getIcon());

            categoryRepository.save(existingCategory);
            return existingCategory;
        }

        return null;
    }

    public Category deleteCategoryGroup(Long id) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);

        if(existingCategory != null) {
            categoryRepository.deleteById(id);
            return existingCategory;
        }

        return null;
    }
}
