package com.lucorda.dflow.data.query.service;

import com.lucorda.dflow.data.api.dto.DataApiRequest;
import com.lucorda.dflow.data.query.entity.CompanyInfo;
import com.lucorda.dflow.data.query.entity.Sector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Sql(value = {"classpath:init-test.sql", "classpath:company_info.sql"})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataServiceUnitTest {

    DataService dataService;

    public DataServiceUnitTest(@Autowired DataService dataService) {
        this.dataService = dataService;
    }

    @Test
    @DisplayName("find all sectors")
    void findAllSectors() {
        List<Sector> sectorList = dataService.findAllSectors();
        assertEquals(191, sectorList.size());
    }

    @ParameterizedTest
    @DisplayName("find company info with data api request")
    @MethodSource("provideDataApiRequest")
    void findCompanyInfoWithDataRequest(DataApiRequestToResult dataApiRequest) {
        Page<CompanyInfo> companyInfoPage = dataService.findCompanyInfoWithRequestCondition(
                dataApiRequest.dataApiRequest, dataApiRequest.pageRequest);
        assertEquals(dataApiRequest.expected, companyInfoPage.getTotalElements());
    }

    static Stream<DataApiRequestToResult> provideDataApiRequest() {
        return Stream.of(
                DataApiRequestToResult.of(DataApiRequest.builder()
                                .sectorCodeList(Arrays.asList("02_03_06_P", "08_26_04_P"))
                                .districtCodeList(Arrays.asList(3690000, 5540000, 3040000, 3740000, 3070000))
                                .build(),
                        PageRequest.of(0, 10000),
                        1163L),
                DataApiRequestToResult.of(DataApiRequest.builder()
                                .sectorCodeList(Arrays.asList("02_03_06_P", "08_26_04_P", "01_01_06_P"))
                                .districtCodeList(Arrays.asList(3690000, 5540000, 3040000, 3740000, 3070000))
                                .keywordToFindCompany("약국")
                                .build(),
                        PageRequest.of(0, 100),
                        4L),
                DataApiRequestToResult.of(DataApiRequest.builder()
                                .sectorCodeList(Arrays.asList("02_03_06_P", "08_26_04_P", "01_01_06_P"))
                                .districtCodeList(Arrays.asList(3690000, 5540000, 3040000, 3740000, 3070000))
                                .startDate(LocalDate.of(2022, 1, 5))
                                .endDate(LocalDate.of(2022, 5, 1))
                                .keywordToFindCompany("유통")
                                .build(),
                        PageRequest.of(0, 100),
                        9L),
                DataApiRequestToResult.of(DataApiRequest.builder()
                                .startDate(LocalDate.of(2022, 1, 5))
                                .endDate(LocalDate.of(2022, 5, 1))
                                .build(),
                        PageRequest.of(0, 1000000),
                        40780L)
        );
    }

    static class DataApiRequestToResult {
        DataApiRequest dataApiRequest;

        PageRequest pageRequest;

        Long expected;

        public DataApiRequestToResult(DataApiRequest dataApiRequest, PageRequest pageRequest, Long expected) {
            this.dataApiRequest = dataApiRequest;
            this.pageRequest = pageRequest;
            this.expected = expected;
        }

        public static DataApiRequestToResult of(DataApiRequest dataApiRequest, PageRequest pageRequest, Long expected) {
            return new DataApiRequestToResult(dataApiRequest, pageRequest, expected);
        }
    }
}