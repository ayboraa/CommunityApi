package org.community.api.mapper;

import org.community.api.common.CommentId;
import org.community.api.common.MemberId;
import org.community.api.common.PostId;
import org.community.api.entity.CommentEntity;
import org.community.api.dto.admin.AdminCommentDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper implements Mapper<AdminCommentDTO, CommentEntity> {
    @Override
    public CommentEntity toEntity(AdminCommentDTO comment) {
        return new CommentEntity(comment.getId().uuid(), comment.getContent(), comment.getAuthorId().uuid(), comment.getPostId().uuid());
    }


    @Override
    public AdminCommentDTO toDTO(CommentEntity entity) {
        return new AdminCommentDTO(new CommentId(entity.getId()), new MemberId(entity.getAuthorId()), entity.getContent(), new PostId(entity.getPostId()));
    }

    @Override
    public List<AdminCommentDTO> toDTOList(List<CommentEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}