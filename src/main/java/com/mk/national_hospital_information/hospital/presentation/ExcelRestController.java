package com.mk.national_hospital_information.hospital.presentation;

import com.mk.national_hospital_information.hospital.application.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "📑 0. Excel Controller", description = "전국 병/의원 엑셀 데이터 DB 저장")
public class ExcelRestController {

    private final ExcelService excelService;

    @PostMapping("/data-upload")
    @Operation(summary = "✔ 엑셀 데이터 DB 저장", description = "📢 ADMIN(관리자)만 관리 가능합니다.")
    public ResponseEntity<String> uploadData(@RequestParam("filePath") String filePath) {
        excelService.saveHospitalsFromExcel(filePath);
        return ResponseEntity.ok("엑셀 데이터가 db에 성공적으로 저장되었습니다.");
    }

}
