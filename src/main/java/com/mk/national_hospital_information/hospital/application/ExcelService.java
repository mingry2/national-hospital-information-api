package com.mk.national_hospital_information.hospital.application;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelService {

    private final HospitalRepository hospitalRepository;

    @Transactional
    public void saveHospitalsFromExcel(String filePath) {

        List<HospitalEntity> hospitalEntities = new ArrayList<>();

        try (
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            Workbook workbook = new XSSFWorkbook(bis);
        ) {
            Sheet sheet = workbook.getSheetAt(0); // 첫번째 시트 읽기

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                Hospital hospital = new Hospital(
                    getCellValue(row.getCell(1)),
                    getCellValue(row.getCell(10)),
                    getCellValue(row.getCell(11)),
                    getCellValue(row.getCell(12))
                );

                hospitalEntities.add(hospital.toEntity());

                if (hospitalEntities.size() >= 1000) {
                    hospitalRepository.saveAll(hospitalEntities);
                    hospitalEntities.clear();
                }

                if (!hospitalEntities.isEmpty()) {
                    hospitalRepository.saveAll(hospitalEntities);
                }

            }
        } catch (IOException e) {
            log.error("파일 읽기 오류", e);
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default -> "";
        };
    }
}
