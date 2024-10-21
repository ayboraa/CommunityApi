package org.community.api.controller;


import jakarta.validation.Valid;
import org.community.api.common.PostId;
import org.community.api.controller.exception.UnauthorizedException;
import org.community.api.service.MemberService;
import org.community.api.dto.admin.AdminPostDTO;
import org.community.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/posts", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping(value = "/", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<List<AdminPostDTO>> getPosts() {
        List<AdminPostDTO> categories = postService.getAllPosts();

        return ResponseEntity.ok(categories);
    }


    @GetMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<AdminPostDTO> getPostById(@PathVariable UUID id) {
        AdminPostDTO post = postService.findPostById(new PostId(id));
        return ResponseEntity.ok(post);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AdminPostDTO> updatePost(@PathVariable UUID id, @RequestBody @Valid AdminPostDTO newPost) {

        var thePost = postService.findPostById(new PostId(id));
        if(!MemberService.isCurrentUserAdmin() && (thePost.getAuthorId() != MemberService.getCurrentUserId()))
            throw new UnauthorizedException("You don't have rights to edit this post.");

        AdminPostDTO updatedPostData = postService.updatePost(new PostId(id), newPost);
        return ResponseEntity.ok(updatedPostData);
    }

    @PostMapping("/")
    public ResponseEntity<AdminPostDTO> createPost(@RequestBody @Valid AdminPostDTO newPost) {
        AdminPostDTO createdPost = postService.savePost(newPost);
        return ResponseEntity.ok(createdPost);
    }


    @DeleteMapping(value = "/{id}", consumes = MediaType.ALL_VALUE)
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {


        var thePost = postService.findPostById(new PostId(id));
        if(!MemberService.isCurrentUserAdmin() && (thePost.getAuthorId() != MemberService.getCurrentUserId()))
            throw new UnauthorizedException("You don't have rights to delete this post.");

        postService.deletePost(new PostId(id));
        return ResponseEntity.ok().build();
    }



}
