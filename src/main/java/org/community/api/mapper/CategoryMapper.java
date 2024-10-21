package org.community.api.mapper;

import org.community.api.common.CategoryId;
import org.community.api.entity.CategoryEntity;
import org.community.api.dto.admin.AdminCategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CategoryMapper implements Mapper<AdminCategoryDTO, CategoryEntity> {
    @Override
    public CategoryEntity toEntity(AdminCategoryDTO category) {
        return new CategoryEntity(category.getId().uuid(), category.getName());
    }

    @Override
    public AdminCategoryDTO toDTO(CategoryEntity entity) {
        return new AdminCategoryDTO(new CategoryId(entity.getId()), entity.getName());
    }

    @Override
    public List<AdminCategoryDTO> toDTOList(List<CategoryEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}