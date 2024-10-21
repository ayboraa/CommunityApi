package org.community.api;


import org.community.api.service.CategoryService;
import org.community.api.dto.admin.AdminCategoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest
public class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testSingleCRUD() {

        // Test create.
        AdminCategoryDTO category = new AdminCategoryDTO(null, "Fitness.");
        AdminCategoryDTO savedCategory = categoryService.saveCategory(category);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Fitness.");

        // Test read.
        AdminCategoryDTO foundCategory = categoryService.findCategoryById(savedCategory.getId());
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Fitness.");

        // Test update.
        AdminCategoryDTO toUpdate = new AdminCategoryDTO(foundCategory.getId(), "Fitness 2.");
        AdminCategoryDTO updatedCategory = categoryService.updateCategory(savedCategory.getId(), toUpdate);
        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo("Fitness 2.");
        // Ensure update is correct.
        updatedCategory = categoryService.findCategoryById(updatedCategory.getId());
        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo("Fitness 2.");

        // Test read.
        List<AdminCategoryDTO> categoryList = categoryService.getAllCategories();
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(1);

        // Test delete.
        categoryService.deleteCategory(categoryList.getFirst().getId());
        categoryList = categoryService.getAllCategories();
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(0);

    }
}
