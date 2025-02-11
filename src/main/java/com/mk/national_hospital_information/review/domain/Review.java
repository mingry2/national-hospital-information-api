package com.mk.national_hospital_information.review.domain;

import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Review {

    private Long id;
    private String title;
    private String content;
    private Long satisfaction;
    private Long userId;
    private Long hospitalId;

    public Review(String title, String content, Long satisfaction, User user, Hospital hospital) {
        this.title = title;
        this.content = content;
        this.satisfaction = satisfaction;
        this.userId = user.getId();
        this.hospitalId = hospital.getId();
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", satisfaction=" + satisfaction +
            '}';
    }
}
