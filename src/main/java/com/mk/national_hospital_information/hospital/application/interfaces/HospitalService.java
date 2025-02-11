package com.mk.national_hospital_information.hospital.application.interfaces;

import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HospitalService {

    Hospital add(HospitalRequestDto hospitalAddRequestDto);
    Hospital update(Long hospitalId, HospitalRequestDto hospitalUpdateRequestDto);
    String delete(Long hospitalId);
    Page<Hospital> findAll(Pageable pageable);
    Hospital findById(Long hospitalId);

}
