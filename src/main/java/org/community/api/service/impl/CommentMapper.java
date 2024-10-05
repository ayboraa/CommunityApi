package org.community.api.service.impl;

import org.community.api.common.CommentId;
import org.community.api.common.MemberId;
import org.community.api.entity.CommentEntity;
import org.community.api.service.Comment;
import org.community.api.service.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper implements Mapper<Comment, CommentEntity> {
    @Override
    public CommentEntity toEntity(Comment comment) {
        return new CommentEntity(comment.getId().uuid(), comment.getContent(), comment.getAuthorId().uuid());
    }

    @Override
    public Comment toDTO(CommentEntity entity) {
        return new Comment(new CommentId(entity.getId()), new MemberId(entity.getAuthorId()), entity.getContent());
    }

    @Override
    public List<Comment> toDTOList(List<CommentEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }
}
