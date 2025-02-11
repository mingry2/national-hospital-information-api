package com.mk.national_hospital_information.hospital.infrastructure;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import com.mk.national_hospital_information.hospital.infrastructure.jpa.HospitalJpaRepository;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class HospitalRepositoryImpl implements HospitalRepository {

    private final HospitalJpaRepository hospitalJpaRepository;

    @Override
    public Hospital save(Hospital hospital) {
        HospitalEntity hospitalEntity = new HospitalEntity(hospital);
        return hospitalJpaRepository.save(hospitalEntity).toHospital();
    }

    @Override
    public Hospital findById(Long id) {
        HospitalEntity hospitalEntity = hospitalJpaRepository.findById(id)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.HOSPITAL_NOT_FOUND,
                ErrorCode.HOSPITAL_NOT_FOUND.getMessage()
            ));
        return hospitalEntity.toHospital();
    }

    @Override
    public void saveAll(List<HospitalEntity> hospitalEntities) {
        hospitalJpaRepository.saveAll(hospitalEntities);
    }

    @Override
    public String deleteById(Long id) {
        HospitalEntity hospitalEntity = hospitalJpaRepository.findById(id)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.HOSPITAL_NOT_FOUND,
                ErrorCode.HOSPITAL_NOT_FOUND.getMessage()
            ));
        hospitalJpaRepository.deleteById(hospitalEntity.getId());
        return "deleted";
    }

    @Override
    public Hospital update(Long hospitalId, HospitalRequestDto hospitalUpdateRequestDto) {
        HospitalEntity hospitalEntity = hospitalJpaRepository.findById(hospitalId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.HOSPITAL_NOT_FOUND,
                ErrorCode.HOSPITAL_NOT_FOUND.getMessage()
            ));

        hospitalEntity.setHospitalName(hospitalUpdateRequestDto.hospitalName());
        hospitalEntity.setAddress(hospitalUpdateRequestDto.address());
        hospitalEntity.setTel(hospitalUpdateRequestDto.tel());
        hospitalEntity.setWebsite(hospitalUpdateRequestDto.website());

        return hospitalJpaRepository.save(hospitalEntity).toHospital();
    }

    @Override
    public Page<HospitalEntity> findAll(Pageable pageable) {
        return hospitalJpaRepository.findAll(pageable);
    }
}
