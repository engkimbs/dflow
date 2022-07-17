package com.lucorda.dflow.data.query.dao;

import com.lucorda.dflow.data.api.dto.DataApiRequest;
import com.lucorda.dflow.data.query.entity.CompanyInfo;
import com.lucorda.dflow.data.query.entity.District;
import com.lucorda.dflow.data.query.entity.Sector;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@ActiveProfiles("test")
@Sql(value = {"classpath:init-test.sql", "classpath:company_info.sql"})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DataDaoUnitTest {

    DistrictDao districtDao;

    SectorDao sectorDao;

    CompanyInfoDao companyInfoDao;

    public DataDaoUnitTest(@Autowired DistrictDao districtDao,
                           @Autowired SectorDao sectorDao,
                           @Autowired CompanyInfoDao companyInfoDao) {
        this.districtDao = districtDao;
        this.sectorDao = sectorDao;
        this.companyInfoDao = companyInfoDao;
    }

    @Test
    @DisplayName("insert district")
    void findAllDistrict() {
        List<District> districts = districtDao.findAll();

        assertEquals(229, districts.size());
    }

    @Test
    @DisplayName("insert sector")
    void findAllSector() {
        List<Sector> sectors = sectorDao.findAll();

        assertEquals(191, sectors.size());
    }

    @Test
    @DisplayName("find all company info")
    void findAllCompanyInfo() {
        List<CompanyInfo> companyInfoList = companyInfoDao.findAll();

        assertEquals(46975, companyInfoList.size());
    }

    @ParameterizedTest
    @DisplayName("find all company info with request condition")
    @MethodSource("provideDataApiRequest")
    void findAllCompanyInfoWithRequestCondition(DataApiRequestToResult dataApiRequest) {
        Page<CompanyInfo> companyInfoPage = companyInfoDao.findByRequestCondition(
                dataApiRequest.dataApiRequest,
                dataApiRequest.pageRequest);

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