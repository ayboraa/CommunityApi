package org.community.api.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Entity
@Schema(hidden = true)
public class CategoryEntity {
    @Id
    private UUID id;
    private String name;

    public CategoryEntity() {
    }

    public CategoryEntity(UUID id,  String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
