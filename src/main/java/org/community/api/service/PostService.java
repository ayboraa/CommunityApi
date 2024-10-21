package org.community.api.service;


import jakarta.transaction.Transactional;
import org.community.api.common.PostId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.dto.admin.AdminPostDTO;
import org.community.api.entity.PostEntity;
import org.community.api.repository.PostRepository;
import org.community.api.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    @Autowired
    private PostRepository postRepository;

    private final PostMapper _mapper = new PostMapper();

    public AdminPostDTO savePost(AdminPostDTO post){
        PostEntity entity = new PostEntity(post.getId().uuid(), post.getTitle(), post.getContent(), post.getCategoryId().uuid(), post.getAuthorId().uuid());
        postRepository.save(entity);
        return _mapper.toDTO(entity);
    }


    public AdminPostDTO findPostById(PostId id) {
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

    public List<AdminPostDTO> getAllPosts() {
        List<PostEntity> entities = postRepository.findAll();
        return _mapper.toDTOList(entities);
    }

    @Transactional
    public AdminPostDTO updatePost(PostId id, AdminPostDTO newPost) {
        return postRepository.findById(id.uuid())
                .map(postEntity -> {
                    postEntity.setContent(newPost.getTitle());
                    postEntity.setContent(newPost.getContent());
                    return _mapper.toDTO(postRepository.save(postEntity));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + id.uuid()));
    }


}
