package org.community.api.service.impl;

import org.community.api.common.PostId;
import org.community.api.entity.PostEntity;
import org.community.api.service.Mapper;
import org.community.api.service.Post;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper implements Mapper<Post, PostEntity> {
    @Override
    public PostEntity toEntity(Post post) {
        return new PostEntity(post.getId().uuid(), post.getTitle(), post.getContent());
    }

    @Override
    public Post toDTO(PostEntity entity) {
        return new Post(new PostId(entity.getId()), entity.getTitle(), entity.getContent());
    }

    @Override
    public List<Post> toDTOList(List<PostEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}
