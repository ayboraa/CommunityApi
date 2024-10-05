package org.community.api.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class CommentEntity {

    @Id
    private UUID id;
    private String content;
    private UUID authorId;

    public CommentEntity() {}

    public CommentEntity(UUID id, String content, UUID authorId) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public UUID getAuthorId() {
        return authorId;
    }
    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

}
