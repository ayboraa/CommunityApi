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
    private UUID postId;
    // todo: Created at



    public CommentEntity(UUID id, String content, UUID authorId, UUID postId) {
        this.id = id;
        this.content = content;
        this.authorId = authorId;
        this.postId = postId;
    }

    public CommentEntity() {}

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


    public UUID getPostId() {
        return postId;
    }
    public void setPostId(UUID postId) {
        this.postId = postId;
    }

}
