package org.community.api;


import org.community.api.common.MemberId;
import org.community.api.service.Comment;
import org.community.api.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void testSingleCRUD() {

        // Test create.
        Comment comment = new Comment(null, new MemberId(), "Sample comment.");
        Comment savedComment = commentService.saveComment(comment);
        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getContent()).isEqualTo("Sample comment.");

        // Test read.
        Comment foundComment = commentService.findCommentById(savedComment.getId());
        assertThat(foundComment).isNotNull();
        assertThat(foundComment.getContent()).isEqualTo("Sample comment.");

        // Test update.
        Comment toUpdate = new Comment(foundComment.getId(), comment.getAuthorId(), "Updated comment.");
        Comment updatedComment = commentService.updateComment(savedComment.getId(), toUpdate);
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getContent()).isEqualTo("Updated comment.");
        // Ensure update is correct.
        updatedComment = commentService.findCommentById(updatedComment.getId());
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getContent()).isEqualTo("Updated comment.");

        // Test read.
        List<Comment> commentList = commentService.getAllComments();
        assertThat(commentList).isNotNull();
        assertThat(commentList.size()).isEqualTo(1);

        // Test delete.
        commentService.deleteComment(commentList.getFirst().getId());
        commentList = commentService.getAllComments();
        assertThat(commentList).isNotNull();
        assertThat(commentList.size()).isEqualTo(0);

    }
}
