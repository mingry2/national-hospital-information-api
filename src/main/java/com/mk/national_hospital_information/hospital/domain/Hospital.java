package com.mk.national_hospital_information.hospital.domain;

import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Hospital {

    private Long id;
    private String hospitalName;
    private String address;
    private String tel;
    private String website;
    private Long userId;

    public Hospital(String hospitalName, String address, String tel, String website) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.tel = tel;
        this.website = website;
    }

    public Hospital(Long loginId, HospitalRequestDto hospitalAddRequestDto) {
        this.hospitalName = hospitalAddRequestDto.hospitalName();
        this.address = hospitalAddRequestDto.address();
        this.tel = hospitalAddRequestDto.tel();
        this.website = hospitalAddRequestDto.website();
        this.userId = loginId;
    }

    public HospitalEntity toEntity() {
        return new HospitalEntity(
            this.hospitalName,
            this.address,
            this.tel,
            this.website
        );
    }

    @Override
    public String toString() {
        return "Hospital{" +
            "id=" + id +
            ", hospitalName='" + hospitalName + '\'' +
            ", address='" + address + '\'' +
            ", tel='" + tel + '\'' +
            ", website='" + website + '\'' +
            ", userId=" + userId +
            '}';
    }
}
