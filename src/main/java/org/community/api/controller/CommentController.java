package org.community.api.controller;


import jakarta.validation.Valid;
import org.community.api.common.CommentId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.controller.exception.UnauthorizedException;
import org.community.api.dto.admin.AdminCommentDTO;
import org.community.api.service.CommentService;
import org.community.api.service.MemberService;
import org.community.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @Autowired
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }


    @GetMapping(value = "/", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<AdminCommentDTO>> getComments() {
        List<AdminCommentDTO> categories = commentService.getAllComments();

        return ResponseEntity.ok(categories);
    }


    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AdminCommentDTO> getCommentById(@PathVariable UUID id) {
        AdminCommentDTO comment = commentService.findCommentById(new CommentId(id));
        return ResponseEntity.ok(comment);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AdminCommentDTO> updateComment(@PathVariable UUID id, @RequestBody @Valid AdminCommentDTO newComment) {

        var thePost = commentService.findCommentById(new CommentId(id));
        if(!MemberService.isCurrentUserAdmin() && (thePost.getAuthorId() != MemberService.getCurrentUserId()))
            throw new UnauthorizedException("You don't have rights to edit this post.");


        AdminCommentDTO updatedCommentData = commentService.updateComment(new CommentId(id), newComment);
        return ResponseEntity.ok(updatedCommentData);
    }

    @PostMapping("/")
    public ResponseEntity<AdminCommentDTO> createComment(@RequestBody @Valid AdminCommentDTO newComment) {

        try {
            var thePost = postService.findPostById(newComment.getPostId());

            AdminCommentDTO createdComment = commentService.saveComment(new AdminCommentDTO(null, MemberService.getCurrentUserId(), newComment.getContent(), thePost.getId()));
            return ResponseEntity.ok(createdComment);

        }
        catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id) {

        var thePost = commentService.findCommentById(new CommentId(id));
        if(!MemberService.isCurrentUserAdmin() && (thePost.getAuthorId() != MemberService.getCurrentUserId()))
            throw new UnauthorizedException("You don't have rights to edit this post.");


        commentService.deleteComment(new CommentId(id));
        return ResponseEntity.ok().build();
    }



}
