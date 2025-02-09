package com.mk.national_hospital_information.hospital.application;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public Hospital add(HospitalRequestDto hospitalAddRequestDto) {
        Hospital hospital = new Hospital(hospitalAddRequestDto.hospitalName(),
            hospitalAddRequestDto.address(),
            hospitalAddRequestDto.tel(),
            hospitalAddRequestDto.website());
        return hospitalRepository.save(hospital);
    }

    public Hospital update(Long hospitalId, HospitalRequestDto hospitalUpdateRequestDto) {
        return hospitalRepository.update(hospitalId, hospitalUpdateRequestDto);
    }

    public String delete(Long hospitalId) {
        return hospitalRepository.deleteById(hospitalId);
    }

    public Page<Hospital> findAll(Pageable pageable) {
        Page<HospitalEntity> hospitalEntities = hospitalRepository.findAll(pageable);

        List<Hospital> hospitals = new ArrayList<>();
        for (HospitalEntity hospitalEntity : hospitalEntities) {
            hospitals.add(hospitalEntity.toHospital());
        }

        return new PageImpl<>(hospitals, pageable, hospitalEntities.getTotalElements());
    }

    public Hospital findById(Long hospitalId) {
        return hospitalRepository.findById(hospitalId);
    }
}
