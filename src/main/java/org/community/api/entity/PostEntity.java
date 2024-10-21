package org.community.api.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class PostEntity {
    @Id
    private UUID id;
    private String title;
    private String content;
    private UUID CategoryId;
    private UUID MemberId;
    // todo: Created at


    public PostEntity(UUID id, String title, String content, UUID CategoryId, UUID MemberId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.CategoryId = CategoryId;
        this.MemberId = MemberId;
    }

    public PostEntity() {
    }

    public UUID getCategoryId(){return CategoryId;}

    public UUID getAuthorId(){return MemberId;}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }





}
