package com.mk.national_hospital_information.hospital.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalService;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalFindResponseDto;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalResponseDto;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final HospitalService hospitalService;
    private final UserService userService;

    @PostMapping("/hospital")
    public ResponseEntity<Response<HospitalResponseDto>> addHospital(@RequestBody HospitalRequestDto hospitalAddRequestDto) {
        Long loginId = getUserId();

        Hospital saveddHospital = hospitalService.save(loginId, hospitalAddRequestDto);

        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto(saveddHospital.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalResponseDto));
    }

    @PutMapping("/hospital/{hospitalId}")
    public ResponseEntity<Response<HospitalResponseDto>> updateHospital(@PathVariable Long hospitalId, @RequestBody HospitalRequestDto hospitalUpdateRequestDto) {
        Long loginId = getUserId();

        Hospital updatedHospital = hospitalService.update(hospitalId, loginId, hospitalUpdateRequestDto);

        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto(updatedHospital.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalResponseDto));
    }

    @PatchMapping("/hospital/{hospitalId}")
    public ResponseEntity<String> deleteHospital(@PathVariable Long hospitalId) {
        Long loginId = getUserId();
        String result = hospitalService.delete(hospitalId, loginId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/hospital/{hospitalId}")
    public ResponseEntity<Response<HospitalFindResponseDto>> findHospital(@PathVariable Long hospitalId) {
        Hospital findHospital = hospitalService.findById(hospitalId);

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

    @GetMapping("/hospitals")
    public Page<Hospital> findAllHospitals(Pageable pageable) {

        return hospitalService.findAll(pageable);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
