package org.community.api.service;


import jakarta.transaction.Transactional;
import org.community.api.common.CommentId;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.entity.CommentEntity;
import org.community.api.repository.CommentRepository;
import org.community.api.service.impl.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    private final CommentMapper _mapper = new CommentMapper();

    public Comment saveComment(Comment comment){
        CommentEntity entity = new CommentEntity(comment.getId().uuid(), comment.getContent(), comment.getAuthorId().uuid());
        commentRepository.save(entity);
        return _mapper.toDTO(entity);
    }


    public Comment findCommentById(CommentId id) {
        Optional<CommentEntity> opt = commentRepository.findById(id.uuid());
        if(opt.isEmpty())
            throw new ResourceNotFoundException("Comment with ID " + id.uuid() + " not found.");
        else
            return _mapper.toDTO(opt.get());
    }

    public void deleteComment(CommentId id) {

        if (!commentRepository.existsById(id.uuid())) {
            throw new ResourceNotFoundException("Comment not found with id: " + id.uuid());
        }

        commentRepository.deleteById(id.uuid());
    }

    public List<Comment> getAllComments() {
        List<CommentEntity> entities = commentRepository.findAll();
        return _mapper.toDTOList(entities);
    }

    @Transactional
    public Comment updateComment(CommentId id, Comment newComment) {
        return commentRepository.findById(id.uuid())
                .map(commentEntity -> {
                    commentEntity.setContent(newComment.getContent());
                    return _mapper.toDTO(commentRepository.save(commentEntity));
                })
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + id.uuid()));
    }


}
