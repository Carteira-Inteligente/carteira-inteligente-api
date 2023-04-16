package br.com.carteirainteligente.api.service;

import br.com.carteirainteligente.api.model.CategoryGroup;
import br.com.carteirainteligente.api.repository.CategoryGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryGroupService {

    @Autowired
    CategoryGroupRepository categoryGroupRepository;

    public List<CategoryGroup> listCategoryGroups() {
        return categoryGroupRepository.findAll();
    }

    public CategoryGroup getCategoryGroup(Long id) {
        return categoryGroupRepository.findById(id).orElse(null);
    }

    public CategoryGroup saveCategoryGroup(CategoryGroup categoryGroup) {
        return categoryGroupRepository.save(categoryGroup);
    }

    public CategoryGroup updateCategoryGroup(Long id, CategoryGroup categoryGroup) {
        CategoryGroup existingCategoryGroup = categoryGroupRepository.findById(id).orElse(null);

        if(existingCategoryGroup != null) {
            existingCategoryGroup.setUser(categoryGroup.getUser());
            existingCategoryGroup.setDescription(categoryGroup.getDescription());
            existingCategoryGroup.setIcon(categoryGroup.getIcon());

            categoryGroupRepository.save(existingCategoryGroup);
            return existingCategoryGroup;
        }

        return null;
    }

    public CategoryGroup deleteCategoryGroup(Long id) {
        CategoryGroup existingCategoryGroup = categoryGroupRepository.findById(id).orElse(null);

        if(existingCategoryGroup != null) {
            categoryGroupRepository.deleteById(id);
            return existingCategoryGroup;
        }

        return null;
    }
}
