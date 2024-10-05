package org.community.api.service;

import jakarta.annotation.Nullable;
import org.community.api.common.PostId;
import org.springframework.util.Assert;

public class Post {
    private PostId id;
    private String title;
    private String content;


        public Post(@Nullable PostId postId, String title, String content) {
            Assert.notNull(title, "Title cannot be null");
            Assert.notNull(content, "Content cannot be null");

            this.id = (postId == null) ? new PostId() : postId;
            this.title = title;
            this.content = content;

        }


    public PostId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }



}
