package org.community.api.service;


import jakarta.transaction.Transactional;
import org.community.api.common.CategoryId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.dto.admin.AdminCategoryDTO;
import org.community.api.entity.CategoryEntity;
import org.community.api.repository.CategoryRepository;
import org.community.api.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private final CategoryMapper _mapper = new CategoryMapper();

    public AdminCategoryDTO saveCategory(AdminCategoryDTO category){
        CategoryEntity entity = new CategoryEntity(new CategoryId().uuid(),  category.getName());
        categoryRepository.save(entity);
        return _mapper.toDTO(entity);
    }


    public AdminCategoryDTO findCategoryById(CategoryId id) {
        Optional<CategoryEntity> opt = categoryRepository.findById(id.uuid());
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Category with ID " + id.uuid() + " not found.");
        else
            return _mapper.toDTO(opt.get());
    }

    public void deleteCategory(CategoryId id) {

        if(!categoryRepository.existsById(id.uuid())){
            throw new ResourceNotFoundException("Category with ID " + id.uuid() + " not found.");
        }

        categoryRepository.deleteById(id.uuid());
    }

    public List<AdminCategoryDTO> getAllCategories() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        return _mapper.toDTOList(entities);
    }


    @Transactional
    public AdminCategoryDTO updateCategory(CategoryId id, AdminCategoryDTO newCategory) {
        return categoryRepository.findById(id.uuid())
                .map(categoryEntity -> {
                    categoryEntity.setName(newCategory.getName());
                    return _mapper.toDTO(categoryRepository.save(categoryEntity));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + id.uuid()));
    }


}


