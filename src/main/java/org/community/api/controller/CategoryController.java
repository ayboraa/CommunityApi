package org.community.api.controller;


import jakarta.validation.Valid;
import org.community.api.common.CategoryId;
import org.community.api.dto.admin.AdminCategoryDTO;
import org.community.api.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<AdminCategoryDTO>> getCategories() {
        List<AdminCategoryDTO> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }


    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AdminCategoryDTO> getCategoryById(@PathVariable UUID id) {
        AdminCategoryDTO category = categoryService.findCategoryById(new CategoryId(id));
        return ResponseEntity.ok(category);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AdminCategoryDTO> updateCategory(@PathVariable UUID id, @RequestBody @Valid AdminCategoryDTO newCategory) {
        AdminCategoryDTO updatedCategoryData = categoryService.updateCategory(new CategoryId(id), newCategory);
        return ResponseEntity.ok(updatedCategoryData);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<AdminCategoryDTO> createCategory(@RequestBody @Valid AdminCategoryDTO newCategory) {
        AdminCategoryDTO createdCategory = categoryService.saveCategory(newCategory);
        return ResponseEntity.ok(createdCategory);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(new CategoryId(id));
        return ResponseEntity.ok().build();
    }



}
