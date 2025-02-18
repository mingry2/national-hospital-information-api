package com.mk.national_hospital_information.review.comment.infrastructure.entity;

import com.mk.national_hospital_information.common.domain.BaseEntity;
import com.mk.national_hospital_information.review.comment.domain.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
@Getter
@SQLRestriction("deleted_at IS NULL")
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private Long userId;

    private Long reviewId;

    public CommentEntity(Comment comment) {
        this.content = comment.getContent();
        this.userId = comment.getUserId();
        this.reviewId = comment.getReviewId();
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public Comment toComment() {
        return new Comment(
            this.id,
            this.content,
            this.userId,
            this.reviewId
        );
    }

}
