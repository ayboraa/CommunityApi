package org.community.api.service;


import jakarta.transaction.Transactional;
import org.community.api.common.PostId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.entity.PostEntity;
import org.community.api.repository.PostRepository;
import org.community.api.service.impl.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    private PostRepository postRepository;

    private final PostMapper _mapper = new PostMapper();

    public Post savePost(Post post){
        PostEntity entity = new PostEntity(post.getId().uuid(), post.getTitle(), post.getContent());
        postRepository.save(entity);
        return _mapper.toDTO(entity);
    }


    public Post findPostById(PostId id) {
        Optional<PostEntity> opt = postRepository.findById(id.uuid());
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Post with ID " + id.uuid() + " not found.");
        else
            return _mapper.toDTO(opt.get());
    }

    public void deletePost(PostId id) {

        if (!postRepository.existsById(id.uuid())) {
            throw new ResourceNotFoundException("Post not found with id: " + id.uuid());
        }

        postRepository.deleteById(id.uuid());
    }

    public List<Post> getAllPosts() {
        List<PostEntity> entities = postRepository.findAll();
        return _mapper.toDTOList(entities);
    }

    @Transactional
    public Post updatePost(PostId id, Post newPost) {
        return postRepository.findById(id.uuid())
                .map(postEntity -> {
                    postEntity.setContent(newPost.getTitle());
                    postEntity.setContent(newPost.getContent());
                    return _mapper.toDTO(postRepository.save(postEntity));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id.uuid()));
    }


}