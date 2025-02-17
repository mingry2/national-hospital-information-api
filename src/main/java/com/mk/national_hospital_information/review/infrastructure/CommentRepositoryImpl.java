package com.mk.national_hospital_information.review.infrastructure;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.review.application.interfaces.CommentRepository;
import com.mk.national_hospital_information.review.domain.Comment;
import com.mk.national_hospital_information.review.infrastructure.entity.CommentEntity;
import com.mk.national_hospital_information.review.infrastructure.jpa.CommentJpaRepository;
import com.mk.national_hospital_information.review.presentation.dto.CommentRequestDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = new CommentEntity(comment);

        return commentJpaRepository.save(commentEntity).toComment();
    }

    @Override
    public Comment findById(Long commentId) {
        CommentEntity commentEntity = commentJpaRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.COMMENT_NOT_FOUND,
                ErrorCode.COMMENT_NOT_FOUND.getMessage()
            ));

        return commentEntity.toComment();
    }

    @Override
    public Comment update(Long commentId, Long loginId, CommentRequestDto commentUpdateRequestDto) {
        CommentEntity oldCommentEntity = commentJpaRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.COMMENT_NOT_FOUND,
                ErrorCode.COMMENT_NOT_FOUND.getMessage()
            ));

        Long writeUserId = oldCommentEntity.getUserId();
        if (!writeUserId.equals(loginId)) {
            throw new GlobalException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 더티 체킹
        oldCommentEntity.updateComment(
            commentUpdateRequestDto.content()
        );

        return oldCommentEntity.toComment();
    }

    @Override
    public void delete(Long commentId, Long loginId) {
        CommentEntity findCommentEntity = commentJpaRepository.findById(commentId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.COMMENT_NOT_FOUND,
                ErrorCode.COMMENT_NOT_FOUND.getMessage()
            ));

        Long writeUserId = findCommentEntity.getUserId();
        if (!writeUserId.equals(loginId)) {
            throw new GlobalException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        // soft delete
        findCommentEntity.setDeletedAt(LocalDateTime.now());
        commentJpaRepository.save(findCommentEntity);
    }

    @Override
    public List<Comment> findAll(Pageable pageable) {
        Page<CommentEntity> commentEntities = commentJpaRepository.findAll(pageable);

        List<Comment> comments = new ArrayList<>();
        for (CommentEntity commentEntity : commentEntities) {
            comments.add(commentEntity.toComment());
        }

        return comments;
    }
}
