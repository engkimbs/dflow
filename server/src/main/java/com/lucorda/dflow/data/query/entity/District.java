package com.lucorda.dflow.data.query.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Immutable
@NoArgsConstructor
public class District {

    @Id
    Integer districtCode;

    String cityName;

    String districtName;

    @Builder
    public District(Integer districtCode, String cityName, String districtName) {
        this.districtCode = districtCode;
        this.cityName = cityName;
        this.districtName = districtName;
    }
}