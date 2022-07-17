package com.lucorda.dflow.data.query.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="company_info")
@Getter
@ToString
@Immutable
@NoArgsConstructor
public class CompanyInfo {

    @EmbeddedId
    CompanyInfoId companyInfoId;

    LocalDate registerDate;

    String status;

    String phoneNumber;

    String address;

    String companyName;

    LocalDateTime startTime;

    LocalDateTime updateTime;
}
