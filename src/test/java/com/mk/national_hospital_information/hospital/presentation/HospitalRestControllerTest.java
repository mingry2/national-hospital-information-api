package com.mk.national_hospital_information.hospital.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.national_hospital_information.config.AbstractMySqlTestContainers;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalService;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.hospital.presentation.dto.HospitalRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class HospitalRestControllerTest extends AbstractMySqlTestContainers {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private HospitalService hospitalService;

    private User user;
    private Hospital hospital;
    private HospitalRequestDto hospitalRequestDto;

    @BeforeEach
    void init() {
        user = new User(1L, "testUser", "password");
        hospital = new Hospital(1L, "testHospitalName", "testAddress", "testTel", "testWebsite", user.getId());
        hospitalRequestDto = new HospitalRequestDto("testHospitalName", "testAddress", "testTel", "testWebsite");
    }

    @Test
    @DisplayName("병원 등록 api 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void addHospital() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(hospitalService.save(anyLong(), any(HospitalRequestDto.class))).thenReturn(hospital);

        mockMvc.perform(post("/api/v1/hospital")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hospitalRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(jsonPath("$.result.id").value(hospital.getId()));
    }

    @Test
    @DisplayName("병원 수정 api 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void updateHospital() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(user);
        Hospital updateHospital = new Hospital(1L, "updateHospitalName", "updateAddress", "updateTel", "updateWebsite", user.getId());
        when(hospitalService.update(anyLong(), anyLong(), any(HospitalRequestDto.class))).thenReturn(updateHospital);
        HospitalRequestDto hospitalUpdateRequestDto = new HospitalRequestDto("updateHospitalName", "updateAddress", "updateTel", "updateWebsite");

        mockMvc.perform(put("/api/v1/hospital/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(hospitalUpdateRequestDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
        .andExpect(jsonPath("$.result.id").value(updateHospital.getId()));
    }

    @Test
    @DisplayName("병원 삭제 api 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void deleteHospital() throws Exception {
        when(userService.findByUsername(anyString())).thenReturn(user);
        when(hospitalService.delete(anyLong(), anyLong())).thenReturn("Hospital Deleted");

        mockMvc.perform(patch("/api/v1/hospital/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value("Hospital Deleted"));
    }

    @Test
    @DisplayName("병원 조회 api 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void getHospital() throws Exception {
        when(hospitalService.findByHospitalId(anyLong())).thenReturn(hospital);

        mockMvc.perform(get("/api/v1/hospital/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(jsonPath("$.result.hospitalId").value(hospital.getId()))
            .andExpect(jsonPath("$.result.hospitalName").value(hospital.getHospitalName()))
            .andExpect(jsonPath("$.result.address").value(hospital.getAddress()))
            .andExpect(jsonPath("$.result.tel").value(hospital.getTel()))
            .andExpect(jsonPath("$.result.website").value(hospital.getWebsite()));
    }

    @Test
    @DisplayName("병원 전체 조회 api 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void getAllHospitals() throws Exception {
        Page<Hospital> page = new PageImpl<>(List.of(new Hospital(1L, "testHospitalName", "testAddress", "testTel", "testWebsite", user.getId())));
        when(hospitalService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/hospitals"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(hospital.getId()))
            .andExpect(jsonPath("$.content[0].hospitalName").value(hospital.getHospitalName()));
    }

}