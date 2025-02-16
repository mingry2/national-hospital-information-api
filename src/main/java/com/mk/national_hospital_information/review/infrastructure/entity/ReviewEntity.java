package com.mk.national_hospital_information.review.infrastructure.entity;

import com.mk.national_hospital_information.common.domain.BaseEntity;
import com.mk.national_hospital_information.review.domain.Review;
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
@Table(name = "review")
@Getter
@SQLRestriction("deleted_at IS NULL")
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String title;

    private String content;

    private Long satisfaction;

    private Long userId;

    private Long hospitalId;

    public ReviewEntity(Review review) {
        this.title = review.getTitle();
        this.content = review.getContent();
        this.satisfaction = review.getSatisfaction();
        this.userId = review.getUserId();
        this.hospitalId = review.getHospitalId();
    }

    public void updateReview(Review review) {
        this.title = review.getTitle();
        this.content = review.getContent();
        this.satisfaction = review.getSatisfaction();
        this.userId = review.getUserId();
        this.hospitalId = review.getHospitalId();
    }

    // Entity -> Domain
    public Review toReview() {
        return new Review(
            this.id,
            this.title,
            this.content,
            this.satisfaction,
            this.userId,
            this.hospitalId
        );
    }
}
