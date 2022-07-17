package com.lucorda.dflow.data.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucorda.dflow.data.api.dto.DataApiRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Slf4j
@ActiveProfiles("test")
@Sql(value = {"classpath:init-test.sql", "classpath:company_info.sql"})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class DataApiControllerIntegrationTest {

    MockMvc mockMvc;

    DataApiController dataApiController;

    ObjectMapper objectMapper;

    public DataApiControllerIntegrationTest(
            @Autowired MockMvc mockMvc,
            @Autowired DataApiController dataApiController,
            @Autowired ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.dataApiController = dataApiController;
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("get all sector list")
    void getAllSectorList() throws Exception {
        mockMvc.perform(get("/api/data/categories"))
                .andDo(print())
                .andExpect(jsonPath("$.count", is(191)))
                ;
    }

    @Test
    @DisplayName("get all district list")
    void getAllDistrictList() throws Exception {
        mockMvc.perform(get("/api/data/districts"))
                .andDo(print())
                .andExpect(jsonPath("$.count", is(229)))
        ;
    }

    @Test
    @DisplayName("get company page with condition")
    void getCompanyPageWithConditionDefault() throws Exception {
        DataApiRequest dataApiRequest = DataApiRequest.builder()
                .sectorCodeList(Arrays.asList("04_17_01_P", "05_18_01_P"))
                .districtCodeList(Arrays.asList(3030000, 3560000))
                .build();

        String request = objectMapper.writeValueAsString(dataApiRequest);

        mockMvc.perform(get("/api/data/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(jsonPath("$.totalElements", is(38)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.size", is(50)))
        ;
    }

    @Test
    @DisplayName("failed to request more then 100 page size")
    void failToCompanyPageWithRequestMoreThen100PageSize() throws Exception {
        DataApiRequest dataApiRequest = DataApiRequest.builder()
                .build();

        String request = objectMapper.writeValueAsString(dataApiRequest);

        mockMvc.perform(get("/api/data/company")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(jsonPath("$.totalElements", is(46976)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.size", is(46976)))
        ;
    }
}
