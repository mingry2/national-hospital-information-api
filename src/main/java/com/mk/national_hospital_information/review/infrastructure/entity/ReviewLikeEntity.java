package com.mk.national_hospital_information.review.infrastructure.entity;

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
@Table(name = "review_like")
@Getter
public class ReviewLikeEntity extends LikeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_like_id")
    private Long id;

    @Setter
    private boolean reviewLike;

    private Long userId;

    private Long reviewId;

    public ReviewLikeEntity(boolean reviewLike, Long userId, Long reviewId) {
        this.reviewLike = reviewLike;
        this.userId = userId;
        this.reviewId = reviewId;
    }
}
