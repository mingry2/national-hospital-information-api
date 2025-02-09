package com.mk.national_hospital_information.hospital.presentation;

import com.mk.national_hospital_information.hospital.application.ExcelService;
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
public class ExcelRestController {

    private final ExcelService excelService;

    @PostMapping("/data-upload")
    public ResponseEntity<String> uploadData(@RequestParam("filePath") String filePath) {
        excelService.saveHospitalsFromExcel(filePath);
        return ResponseEntity.ok("엑셀 데이터가 db에 성공적으로 저장되었습니다.");
    }

}
