package com.mk.national_hospital_information.hospital.application;

import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.infrastructure.entity.HospitalEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelService {

    //    private final HospitalRepository hospitalRepository;

    @PersistenceContext
    private EntityManager entityManager; // Spring Data JPA를 사용하지 않고 EntityManger를 직접 사용

    private static final int BATCH_SIZE = 1000; // 배치크기 설정

    @Transactional
    public void saveHospitalsFromExcel(String filePath) {

        List<HospitalEntity> hospitalEntities = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(bis);) {

            Sheet sheet = xssfWorkbook.getSheetAt(0); // 첫번째 시트 읽기

            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue; // 첫번째 행 스킵(컬럼명 행)

                Hospital hospital = new Hospital(
                    getCellValue(row.getCell(1)),
                    getCellValue(row.getCell(10)),
                    getCellValue(row.getCell(11)),
                    getCellValue(row.getCell(12))
                );

                hospitalEntities.add(hospital.toEntity());

                if (hospitalEntities.size() >= BATCH_SIZE) {
                    batchInsert(hospitalEntities);
                    hospitalEntities.clear(); // 리스트 초기화
                }
            }

            // 남아 있는 데이터 저장
            if (!hospitalEntities.isEmpty()) {
                batchInsert(hospitalEntities);
            }

        } catch (IOException e) {
            log.error("파일 읽기 오류", e);
        }
    }

    @Transactional // 전체 저장이 하나의 트랜잭션에서 이루어지도록 보장
    public void batchInsert(List<HospitalEntity> hospitalEntities) {
        for (int i = 0; i < hospitalEntities.size(); i++) {
            entityManager.persist(hospitalEntities.get(i)); // 개별로 영속 상태로

            if (i % BATCH_SIZE == 0) {
                entityManager.flush(); // BATCH_SIZE 만큼 한번에 DB INSERT
                entityManager.clear(); // 1차 캐시 비우기 (메모리 최적화)
            }
        }

        entityManager.flush();
        entityManager.clear();
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
