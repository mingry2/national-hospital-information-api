package com.mk.national_hospital_information.hospital.infrastructure.entity;

import com.mk.national_hospital_information.common.domain.BaseEntity;
import com.mk.national_hospital_information.hospital.domain.Hospital;
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
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hospital")
@Getter @Setter
@SQLRestriction("deleted_at IS NULL")
public class HospitalEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Long id;

    @Column(name = "hospital_name")
    private String hospitalName;

    private String address;

    private String tel;

    private String website;

    private Long userId;

    public HospitalEntity(Hospital hospital) {
        this.hospitalName = hospital.getHospitalName();
        this.address = hospital.getAddress();
        this.tel = hospital.getTel();
        this.website = hospital.getWebsite();
        this.userId = hospital.getUserId();
    }

    public HospitalEntity(String hospitalName, String address, String tel, String website) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.tel = tel;
        this.website = website;
    }

    public void updateHospital(String hospitalName, String address, String tel, String website) {
        this.hospitalName = hospitalName;
        this.address = address;
        this.tel = tel;
        this.website = website;
    }

    // Entity -> Domain
    public Hospital toHospital() {
        return new Hospital(
            this.id,
            this.hospitalName,
            this.address,
            this.tel,
            this.website,
            this.userId
        );
    }
}
