package com.mk.national_hospital_information.hospital.application.interfaces;

import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface HospitalRepository {

    Hospital save(Hospital hospital);
    Hospital update(Long oldHospitalId, Long loginId, Hospital updatedHospital);
    void delete(Long hospitalId, Long loginId);
    Hospital findById(Long hospitalId);
    List<Hospital> findAll(Pageable pageable);
    void saveAll(List<HospitalEntity> hospitalEntities);
}
