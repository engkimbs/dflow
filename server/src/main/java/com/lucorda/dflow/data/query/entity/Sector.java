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
public class Sector {

    @Id
    String sectorCode;

    String category;

    String sector;

    @Builder
    public Sector(String sectorCode, String category, String sector) {
        this.sectorCode = sectorCode;
        this.category = category;
        this.sector = sector;
    }
}