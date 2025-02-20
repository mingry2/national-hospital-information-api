package com.mk.national_hospital_information.hospital.infrastructure.jpa;

import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalJpaRepository extends JpaRepository<HospitalEntity, Long> {
    Page<HospitalEntity> findAll(Pageable pageable);
}
