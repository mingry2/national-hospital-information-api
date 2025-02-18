package com.mk.national_hospital_information.hospital.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class HospitalServiceImplTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalServiceImpl hospitalService;

    private Hospital hospital;
    private Hospital updateHospital;
    private HospitalRequestDto dto;
    private HospitalRequestDto updateDto;

    @BeforeEach
    void init() {
        dto = new HospitalRequestDto("testHospitalName", "testAddress", "testTel", "testWebsite");
        updateDto = new HospitalRequestDto("updateHospitalName", "updateAddress", "updateTel", "updateWebsite");
        hospital = new Hospital(1L, "testHospitalName", "testAddress", "testTel", "testWebsite", 1L);
        updateHospital = new Hospital(1L, "updateHospitalName", "updateAddress", "updateTel", "updateWebsite", 1L);
    }

    @Test
    @DisplayName("save 성공")
    void save() {
        given(hospitalRepository.save(any(Hospital.class))).willReturn(hospital);

        Hospital result = hospitalService.save(1L, dto);

        assertThat(result).isEqualTo(hospital);
    }

    @Test
    @DisplayName("update 성공")
    void update() {
        given(hospitalRepository.findById(1L)).willReturn(hospital);
        given(hospitalRepository.update(eq(1L), eq(1L), any(HospitalRequestDto.class))).willReturn(updateHospital);

        Hospital updatedHospital = hospitalService.update(1L, 1L, updateDto);

        assertThat(updatedHospital).isEqualTo(updateHospital);
    }

    @Test
    @DisplayName("delete 성공")
    void delete() {
        given(hospitalRepository.findById(1L)).willReturn(hospital);
        doNothing().when(hospitalRepository).delete(1L, 1L);

        String result = hospitalService.delete(1L, 1L);

        assertThat(result).isEqualTo("Hospital deleted");
        verify(hospitalRepository, times(1)).delete(1L, 1L);
    }

    @Test
    @DisplayName("hospital 조회 성공")
    void getHospital() {
        given(hospitalRepository.findById(1L)).willReturn(hospital);

        Hospital result = hospitalService.findByHospitalId(1L);

        assertThat(result).isEqualTo(hospital);
    }

    @Test
    @DisplayName("hospitals 조회 성공")
    void getHospitals() {
        Pageable pageable = PageRequest.of(0, 10);
        given(hospitalRepository.findAll(pageable)).willReturn(List.of(hospital));

        Page<Hospital> result = hospitalService.findAll(pageable);

        assertThat(result.getContent()).containsExactly(hospital);
        assertThat(result.getTotalElements()).isEqualTo(1);
    }

}