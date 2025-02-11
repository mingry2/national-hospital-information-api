package com.mk.national_hospital_information.review.infrastructure.entity;

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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
@Getter
public class ReviewEntity {

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

    @Override
    public String toString() {
        return "ReviewEntity{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", satisfaction=" + satisfaction +
            ", userId=" + userId +
            ", hospitalId=" + hospitalId +
            '}';
    }
}
