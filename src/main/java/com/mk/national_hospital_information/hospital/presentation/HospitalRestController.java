package com.mk.national_hospital_information.hospital.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalService;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalFindResponseDto;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalResponseDto;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "📑 2. Hospital Controller", description = "병원 등록, 수정, 삭제, 조회(단건), 조회(전체)")
public class HospitalRestController {

    private final HospitalService hospitalService;
    private final UserService userService;

    @PostMapping("/hospital")
    @Operation(summary = "✔ 병원 등록", description = "📢 병원이름, 주소, 전화번호, 웹사이트 정보로 병원을 등록합니다.")
    public ResponseEntity<Response<HospitalResponseDto>> addHospital(@RequestBody HospitalRequestDto hospitalAddRequestDto) {
        Long loginId = getUserId();

        Hospital saveddHospital = hospitalService.save(loginId, hospitalAddRequestDto);

        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto(saveddHospital.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalResponseDto));
    }

    @PutMapping("/hospital/{hospitalId}")
    @Operation(summary = "✔ 병원 수정", description = "📢 병원이름, 주소, 전화번호, 웹사이트 정보로 병원을 수정합니다.")
    public ResponseEntity<Response<HospitalResponseDto>> updateHospital(@PathVariable Long hospitalId, @RequestBody HospitalRequestDto hospitalUpdateRequestDto) {
        Long loginId = getUserId();

        Hospital updatedHospital = hospitalService.update(hospitalId, loginId, hospitalUpdateRequestDto);

        HospitalResponseDto hospitalResponseDto = new HospitalResponseDto(updatedHospital.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(hospitalResponseDto));
    }

    @PatchMapping("/hospital/{hospitalId}")
    @Operation(summary = "✔ 병원 삭제", description = "📢 병원을 삭제합니다.(단, 데이터는 완전 삭제되지 않으며, deleted_at을 통해 관리됩니다.(soft delete)")
    public ResponseEntity<String> deleteHospital(@PathVariable Long hospitalId) {
        Long loginId = getUserId();
        String result = hospitalService.delete(hospitalId, loginId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/hospital/{hospitalId}")
    @Operation(summary = "✔ 병원 조회(단건)", description = "📢 단건 병원을 조회합니다.(병원이름, 주소, 전화번호, 웹사이트 정보 표시)")
    public ResponseEntity<Response<HospitalFindResponseDto>> findHospital(@PathVariable Long hospitalId) {
        Hospital findHospital = hospitalService.findByHospitalId(hospitalId);

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
    @Operation(summary = "✔ 병원 조회(전체)", description = "📢 전체 병원을 조회합니다.(병원이름, 주소, 전화번호, 웹사이트 정보 표시 - 1페이지 당 20건)")
    public Page<Hospital> findAllHospitals(Pageable pageable) {

        return hospitalService.findAll(pageable);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
