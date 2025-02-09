package com.mk.national_hospital_information.hospital.domain;

import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Hospital {

    private Long id;
    private String hospitalName;
    private String address;
    private String tel;
    private String website;

    public Hospital(String hospitalName, String address, String tel, String website) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.tel = tel;
        this.website = website;
    }

    public HospitalEntity toEntity() {
        return new HospitalEntity(this.hospitalName, this.address, this.tel, this.website);
    }
}
