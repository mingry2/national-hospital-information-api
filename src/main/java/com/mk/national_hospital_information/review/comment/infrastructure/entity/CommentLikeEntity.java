package com.mk.national_hospital_information.review.comment.infrastructure.entity;

import com.mk.national_hospital_information.common.domain.LikeBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_like")
@Getter
public class CommentLikeEntity extends LikeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long id;

    @Setter
    private boolean commentLike;

    private Long userId;

    private Long commentId;

    public CommentLikeEntity(boolean commentLike, Long userId, Long commentId) {
        this.commentLike = commentLike;
        this.userId = userId;
        this.commentId = commentId;
    }
}
