package com.mk.national_hospital_information;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class NationalHospitalInformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NationalHospitalInformationApplication.class, args);
	}

}
