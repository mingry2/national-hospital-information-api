package com.mk.national_hospital_information.hospital.infrastructure;

import static org.assertj.core.api.Assertions.*;

import com.mk.national_hospital_information.config.TestAuditorAwareConfig;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import com.mk.national_hospital_information.hospital.infrastructure.jpa.HospitalJpaRepository;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestAuditorAwareConfig.class)
class HospitalRepositoryImplTest {

    @Autowired
    private HospitalJpaRepository hospitalJpaRepository;

    private HospitalRepository hospitalRepository;
    private Hospital hospitalA;
    private Hospital hospitalB;
    private HospitalEntity hospitalEntityA;
    private HospitalEntity hospitalEntityB;

    @BeforeEach
    void init() {
        hospitalRepository = new HospitalRepositoryImpl(hospitalJpaRepository);
        HospitalRequestDto dto = new HospitalRequestDto("testHospitalName", "testAddress", "testTel", "testWebsite");
        hospitalA = new Hospital(1L, dto);
        hospitalB = new Hospital(2L, dto);
        hospitalEntityA = new HospitalEntity(hospitalA);
        hospitalEntityB = new HospitalEntity(hospitalB);
    }

    @Test
    @DisplayName("병원 등록 성공")
    void save() {
        HospitalEntity savedHospitalEntity = hospitalJpaRepository.save(hospitalEntityA);
        Hospital savedHospital = savedHospitalEntity.toHospital();

        Hospital result = hospitalRepository.save(savedHospital);

        assertThat(result.getHospitalName()).isEqualTo(hospitalA.getHospitalName());
        assertThat(result.getAddress()).isEqualTo(hospitalA.getAddress());
        assertThat(result.getTel()).isEqualTo(hospitalA.getTel());
        assertThat(result.getWebsite()).isEqualTo(hospitalA.getWebsite());
    }

    @Test
    @DisplayName("병원 수정 성공")
    void update() {
        HospitalEntity oldHospitalEntity = hospitalJpaRepository.save(hospitalEntityA);
        HospitalRequestDto updateDto = new HospitalRequestDto("newHospitalName",
            "newAddress", "newTel", "newWebsite");

        Hospital updatedHospital = hospitalRepository.update(oldHospitalEntity.getId(), 1L, updateDto);

        assertThat(updatedHospital.getHospitalName()).isEqualTo("newHospitalName");
        assertThat(updatedHospital.getAddress()).isEqualTo("newAddress");
        assertThat(updatedHospital.getTel()).isEqualTo("newTel");
        assertThat(updatedHospital.getWebsite()).isEqualTo("newWebsite");
    }

    @Test
    @DisplayName("병원 삭제 성공")
    void delete() {
        HospitalEntity findHospitalEntity = hospitalJpaRepository.save(hospitalEntityA);

        hospitalRepository.delete(findHospitalEntity.getId(), 1L);

        HospitalEntity deletedEntity = hospitalJpaRepository.findById(findHospitalEntity.getId()).orElse(null);
        assertThat(deletedEntity.getDeletedAt()).isNotNull();
    }

    @Test
    void findById() {
        HospitalEntity findHospitalEntity = hospitalJpaRepository.save(hospitalEntityA);

        Hospital hospital = hospitalRepository.findById(findHospitalEntity.getId());

        assertThat(hospital.getHospitalName()).isEqualTo("testHospitalName");
        assertThat(hospital.getAddress()).isEqualTo("testAddress");
        assertThat(hospital.getTel()).isEqualTo("testTel");
        assertThat(hospital.getWebsite()).isEqualTo("testWebsite");
    }

    @Test
    void findAll() {
        hospitalJpaRepository.save(hospitalEntityA);
        hospitalJpaRepository.save(hospitalEntityB);

        Pageable pageable = PageRequest.of(0, 10);
        List<Hospital> hospitals = hospitalRepository.findAll(pageable);

        assertThat(hospitals).hasSize(2);
    }

}