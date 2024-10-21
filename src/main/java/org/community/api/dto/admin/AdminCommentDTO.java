package org.community.api.dto.admin;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.community.api.common.CommentId;
import org.community.api.common.MemberId;
import org.community.api.common.PostId;
import org.springframework.util.Assert;

public class AdminCommentDTO {
    private CommentId id;
    @NotNull
    private MemberId authorId;
    @NotBlank
    private String content;
    @NotNull
    private PostId postId;


    public AdminCommentDTO(@Nullable CommentId commentId, MemberId authorId, String content, PostId postId) {
        Assert.notNull(authorId, "authorId cannot be null");
        Assert.notNull(content, "content cannot be null");
        Assert.notNull(postId, "postId cannot be null");

        this.id = (commentId == null) ? new CommentId() : commentId;
        this.authorId = authorId;
        this.content = content;
        this.postId = postId;
    }


    public CommentId getId() {
        return id;
    }

    public MemberId getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public PostId getPostId() { return postId; }


}
