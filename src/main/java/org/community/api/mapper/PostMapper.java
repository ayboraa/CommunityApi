package org.community.api.mapper;

import org.community.api.common.CategoryId;
import org.community.api.common.MemberId;
import org.community.api.common.PostId;
import org.community.api.entity.PostEntity;
import org.community.api.dto.admin.AdminPostDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper implements Mapper<AdminPostDTO, PostEntity> {
    @Override
    public PostEntity toEntity(AdminPostDTO post) {
        return new PostEntity(post.getId().uuid(), post.getTitle(), post.getContent(), post.getCategoryId().uuid(), post.getAuthorId().uuid());
    }

    @Override
    public AdminPostDTO toDTO(PostEntity entity) {
        return new AdminPostDTO(new PostId(entity.getId()), entity.getTitle(), entity.getContent(), new CategoryId(entity.getCategoryId()), new MemberId(entity.getAuthorId()));
    }

    @Override
    public List<AdminPostDTO> toDTOList(List<PostEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}