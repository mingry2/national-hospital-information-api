package com.mk.national_hospital_information.hospital.infrastructure;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import com.mk.national_hospital_information.hospital.infrastructure.jpa.HospitalJpaRepository;
import java.time.LocalDateTime;
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
    @Transactional
    public Hospital update(Long oldHospitalId, Long loginId, Hospital hospital) {
        HospitalEntity oldHospitalEntity = hospitalJpaRepository.findById(oldHospitalId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.HOSPITAL_NOT_FOUND,
                ErrorCode.HOSPITAL_NOT_FOUND.getMessage()
            ));

        Long writeUserId = oldHospitalEntity.getUserId();

        if (!writeUserId.equals(loginId)){
            throw new GlobalException(
                ErrorCode.INVALID_PERMISSION,
                ErrorCode.INVALID_PERMISSION.getMessage()
            );
        }

        oldHospitalEntity.updateHospital(hospital);

        return oldHospitalEntity.toHospital();
    }

    @Override
    public void delete(Long hospitalId, Long loginId) {
        HospitalEntity findHospitalEntity = hospitalJpaRepository.findById(hospitalId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.HOSPITAL_NOT_FOUND,
                ErrorCode.HOSPITAL_NOT_FOUND.getMessage()
            ));

        Long writeUserId = findHospitalEntity.getUserId();

        if (!writeUserId.equals(loginId)){
            throw new GlobalException(
                ErrorCode.INVALID_PERMISSION,
                ErrorCode.INVALID_PERMISSION.getMessage()
            );
        }

        findHospitalEntity.setDeletedAt(LocalDateTime.now());
        hospitalJpaRepository.save(findHospitalEntity);

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
    public Page<HospitalEntity> findAll(Pageable pageable) {

        return hospitalJpaRepository.findAll(pageable);
    }
}
