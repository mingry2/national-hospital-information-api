package com.mk.national_hospital_information.common.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    // 테스트용
    @GetMapping("/admin")
    public String adminP() {
        return "Admin Controller";
    }

}
