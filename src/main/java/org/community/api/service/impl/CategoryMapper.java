package org.community.api.service.impl;

import org.community.api.common.CategoryId;
import org.community.api.entity.CategoryEntity;
import org.community.api.service.Mapper;
import org.community.api.service.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper implements Mapper<Category, CategoryEntity> {
    @Override
    public CategoryEntity toEntity(Category category) {
        return new CategoryEntity(category.getId().uuid(), category.getName());
    }

    @Override
    public Category toDTO(CategoryEntity entity) {
        return new Category(new CategoryId(entity.getId()), entity.getName());
    }

    @Override
    public List<Category> toDTOList(List<CategoryEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}
