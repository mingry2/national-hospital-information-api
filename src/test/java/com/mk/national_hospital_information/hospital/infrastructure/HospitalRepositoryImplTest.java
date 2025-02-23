package com.mk.national_hospital_information.hospital.infrastructure;

import static org.assertj.core.api.Assertions.*;

import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.config.AbstractMySqlTestContainers;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

class HospitalRepositoryImplTest extends AbstractMySqlTestContainers {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Hospital hospitalA;
    private Hospital hospitalB;
    private Long loginId;

    @BeforeEach
    void init() {
        hospitalA = new Hospital(1L, "HospitalNameA", "AddressA", "TelA", "WebsiteA", 1L);
        hospitalB = new Hospital(2L, "HospitalNameB", "AddressB", "TelB", "WebsiteB", 1L);
        loginId = 1L;
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE hospital");
    }

    @Test
    @DisplayName("병원 등록 성공")
    void save() {
        Hospital savedHospital = hospitalRepository.save(hospitalA);

        assertThat(savedHospital.getHospitalName()).isEqualTo(hospitalA.getHospitalName());
        assertThat(savedHospital.getAddress()).isEqualTo(hospitalA.getAddress());
        assertThat(savedHospital.getTel()).isEqualTo(hospitalA.getTel());
        assertThat(savedHospital.getWebsite()).isEqualTo(hospitalA.getWebsite());
    }

    @Test
    @DisplayName("병원 수정 성공")
    void update() {
        Hospital savedHospital = hospitalRepository.save(hospitalA);
        Hospital oldHospital = hospitalRepository.findById(savedHospital.getId());
        HospitalRequestDto updateDto = new HospitalRequestDto("newHospitalName", "newAddress", "newTel", "newWebsite");

        Hospital updatedHospital = hospitalRepository.update(oldHospital.getId(), loginId, updateDto);

        assertThat(updatedHospital.getId()).isEqualTo(1L);
        assertThat(updatedHospital.getHospitalName()).isEqualTo("newHospitalName");
        assertThat(updatedHospital.getAddress()).isEqualTo("newAddress");
        assertThat(updatedHospital.getTel()).isEqualTo("newTel");
        assertThat(updatedHospital.getWebsite()).isEqualTo("newWebsite");
    }

    @Test
    @DisplayName("병원 삭제 성공")
    void delete() {
        Hospital savedHospital = hospitalRepository.save(hospitalA);

        hospitalRepository.delete(savedHospital.getId(), loginId);

        Assertions.assertThrows(GlobalException.class, () -> hospitalRepository.findById(savedHospital.getId()));
    }

    @Test
    @DisplayName("Id로 Hospital 조회 성공")
    void findById() {
        Hospital savedHospital = hospitalRepository.save(hospitalA);

        Hospital findHospital = hospitalRepository.findById(savedHospital.getId());

        assertThat(findHospital.getHospitalName()).isEqualTo("HospitalNameA");
        assertThat(findHospital.getAddress()).isEqualTo("AddressA");
        assertThat(findHospital.getTel()).isEqualTo("TelA");
        assertThat(findHospital.getWebsite()).isEqualTo("WebsiteA");
    }

    @Test
    @DisplayName("Hospital (전체) 조회 성공")
    void findAll() {
        hospitalRepository.save(hospitalA);
        hospitalRepository.save(hospitalB);

        Pageable pageable = PageRequest.of(0, 10);
        List<Hospital> hospitals = hospitalRepository.findAll(pageable);

        assertThat(hospitals).hasSize(2);
        assertThat(hospitals.get(0).getHospitalName()).isEqualTo("HospitalNameA");
        assertThat(hospitals.get(1).getHospitalName()).isEqualTo("HospitalNameB");
    }

}