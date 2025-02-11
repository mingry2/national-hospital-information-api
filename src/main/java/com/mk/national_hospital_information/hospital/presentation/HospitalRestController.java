package com.mk.national_hospital_information.hospital.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.hospital.application.HospitalServiceImpl;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalFindResponseDto;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalResponseDto;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class HospitalRestController {

    private final HospitalServiceImpl hospitalServiceImpl;

    @PostMapping("/hospital")
    public ResponseEntity<Response<HospitalResponseDto>> addHospital(@RequestBody HospitalRequestDto hospitalAddRequestDto) {
        Hospital addedHospital = hospitalServiceImpl.add(hospitalAddRequestDto);
        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto(addedHospital.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalResponseDto));
    }

    @PutMapping("/hospital/{hospitalId}")
    public ResponseEntity<Response<HospitalResponseDto>> updateHospital(@PathVariable Long hospitalId, @RequestBody HospitalRequestDto hospitalUpdateRequestDto) {
        Hospital updateHospital = hospitalServiceImpl.update(hospitalId, hospitalUpdateRequestDto);
        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto(updateHospital.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalResponseDto));
    }

    @PatchMapping("/hospital/{hospitalId}")
    public ResponseEntity<String> deleteHospital(@PathVariable Long hospitalId) {
        String result = hospitalServiceImpl.delete(hospitalId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/hospitals")
    public Page<Hospital> findAllHospitals(Pageable pageable) {

        return hospitalServiceImpl.findAll(pageable);
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<Response<HospitalFindResponseDto>> findHospital(@PathVariable Long hospitalId) {
        Hospital findHospital = hospitalServiceImpl.findById(hospitalId);
        HospitalFindResponseDto hospitalFindResponseDto = new HospitalFindResponseDto(
            findHospital.getId(),
            findHospital.getHospitalName(),
            findHospital.getAddress(),
            findHospital.getTel(),
            findHospital.getWebsite());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalFindResponseDto));
    }

}
