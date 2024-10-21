package org.community.api.dto.admin;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import org.community.api.common.CategoryId;
import org.springframework.util.Assert;

public class AdminCategoryDTO {
    private CategoryId id;
    @NotBlank
    private String name;


    public AdminCategoryDTO(@Nullable CategoryId categoryId, String name) {
        Assert.notNull(name, "Name cannot be null");


        this.id = (categoryId == null) ? new CategoryId() : categoryId;
        this.name = name;

    }

    public CategoryId getId() {
        return id;
    }


    public String getName() {
        return name;
    }


}
