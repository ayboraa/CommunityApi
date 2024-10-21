package org.community.api.dto.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.community.api.common.CategoryId;
import org.community.api.common.MemberId;
import org.community.api.common.PostId;
import org.springframework.util.Assert;

public class AdminPostDTO {
    private PostId id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    @NotNull
    private CategoryId categoryId;
    @NotNull
    private MemberId authorId;


   public AdminPostDTO(@Nullable PostId postId, String title, String content, CategoryId categoryId, MemberId authorId) {
       Assert.notNull(title, "Title cannot be null");
       Assert.notNull(content, "Content cannot be null");
       Assert.notNull(categoryId, "CategoryId cannot be null");
       Assert.notNull(authorId, "AuthorId cannot be null");

       this.id = (postId == null) ? new PostId() : postId;
       this.title = title;
       this.content = content;
       this.categoryId = categoryId;
       this.authorId = authorId;

   }


    public CategoryId getCategoryId() { return categoryId; }

    public MemberId getAuthorId() { return authorId; }

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
