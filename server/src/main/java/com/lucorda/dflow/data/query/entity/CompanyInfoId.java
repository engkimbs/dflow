package com.lucorda.dflow.data.query.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@Embeddable
public class CompanyInfoId implements Serializable {

    String sectorCode;

    Integer districtCode;

    String managementCode;
}
