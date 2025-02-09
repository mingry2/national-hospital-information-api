package com.mk.national_hospital_information.hospital.application.interfaces;

import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalRepository {

    Hospital save(Hospital hospital);
    Hospital findById(Long id);
    void saveAll(List<HospitalEntity> hospitalEntities);
    String deleteById(Long id);
    Hospital update(Long id, HospitalRequestDto hospitalUpdateRequestDto);
    Page<HospitalEntity> findAll(Pageable pageable);
}
