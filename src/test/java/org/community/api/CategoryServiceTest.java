package org.community.api;


import org.community.api.service.CategoryService;
import org.community.api.service.Category;
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
        Category category = new Category(null, "Fitness.");
        Category savedCategory = categoryService.saveCategory(category);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Fitness.");

        // Test read.
        Category foundCategory = categoryService.findCategoryById(savedCategory.getId());
        assertThat(foundCategory).isNotNull();
        assertThat(foundCategory.getName()).isEqualTo("Fitness.");

        // Test update.
        Category toUpdate = new Category(foundCategory.getId(), "Fitness 2.");
        Category updatedCategory = categoryService.updateCategory(savedCategory.getId(), toUpdate);
        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo("Fitness 2.");
        // Ensure update is correct.
        updatedCategory = categoryService.findCategoryById(updatedCategory.getId());
        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getName()).isEqualTo("Fitness 2.");

        // Test read.
        List<Category> categoryList = categoryService.getAllCategories();
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(1);

        // Test delete.
        categoryService.deleteCategory(categoryList.getFirst().getId());
        categoryList = categoryService.getAllCategories();
        assertThat(categoryList).isNotNull();
        assertThat(categoryList.size()).isEqualTo(0);

    }
}
