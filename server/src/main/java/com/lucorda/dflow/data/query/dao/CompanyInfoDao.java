package com.lucorda.dflow.data.query.dao;

import com.lucorda.dflow.data.query.entity.CompanyInfo;
import com.lucorda.dflow.data.query.entity.CompanyInfoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInfoDao extends JpaRepository<CompanyInfo, CompanyInfoId>, CompanyInfoDaoWithQueryDsl {
}