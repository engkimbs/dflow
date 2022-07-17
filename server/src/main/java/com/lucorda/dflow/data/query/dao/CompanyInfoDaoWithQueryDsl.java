package com.lucorda.dflow.data.query.dao;

import com.lucorda.dflow.data.api.dto.DataApiRequest;
import com.lucorda.dflow.data.query.entity.CompanyInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyInfoDaoWithQueryDsl {

    Page<CompanyInfo> findByRequestCondition(DataApiRequest dataApiRequest, Pageable pageable);
}
