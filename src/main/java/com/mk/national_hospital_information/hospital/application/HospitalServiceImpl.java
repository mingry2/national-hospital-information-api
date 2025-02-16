package com.mk.national_hospital_information.hospital.application;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalService;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;

    @Override
    public Hospital save(Long loginId, HospitalRequestDto hospitalAddRequestDto) {
        Hospital hospital = new Hospital(loginId, hospitalAddRequestDto);

        return hospitalRepository.save(hospital);
    }

    @Override
    @Transactional
    public Hospital update(Long hospitalId, Long loginId, HospitalRequestDto hospitalUpdateRequestDto) {
        // 병원이 존재하는지 검증
        Long oldHospitalId = hospitalRepository.findById(hospitalId).getId();

        Hospital hospital = new Hospital(hospitalUpdateRequestDto);

        return hospitalRepository.update(oldHospitalId, loginId, hospital);
    }

    @Override
    public String delete(Long hospitalId, Long loginId) {
        hospitalRepository.findById(hospitalId);

        hospitalRepository.delete(hospitalId, loginId);

        return "Hospital deleted";
    }

    @Override
    public Page<Hospital> findAll(Pageable pageable) {
        Page<HospitalEntity> hospitalEntities = hospitalRepository.findAll(pageable);

        List<Hospital> hospitals = new ArrayList<>();
        for (HospitalEntity hospitalEntity : hospitalEntities) {
            hospitals.add(hospitalEntity.toHospital());
        }

        return new PageImpl<>(hospitals, pageable, hospitalEntities.getTotalElements());
    }

    @Override
    public Hospital findById(Long hospitalId) {

        return hospitalRepository.findById(hospitalId);
    }
}
