package org.community.api.service;


import jakarta.annotation.Nullable;
import org.community.api.common.CategoryId;
import org.springframework.util.Assert;

public class Category {
    private CategoryId id;
    private String name;


    public Category(@Nullable CategoryId categoryId, String name) {
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
