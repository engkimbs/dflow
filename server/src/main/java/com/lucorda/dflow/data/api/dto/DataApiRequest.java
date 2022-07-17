package com.lucorda.dflow.data.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder
public class DataApiRequest {

    List<String> sectorCodeList;

    List<Integer> districtCodeList;

    @Builder.Default
    LocalDate startDate = LocalDate.of(1, 1, 1);

    @Builder.Default
    LocalDate endDate = LocalDate.of(9999, 12, 31);

    String keywordToFindCompany;
}