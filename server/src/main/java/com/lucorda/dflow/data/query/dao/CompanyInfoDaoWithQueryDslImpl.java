package com.lucorda.dflow.data.query.dao;

import com.lucorda.dflow.data.api.dto.DataApiRequest;
import com.lucorda.dflow.data.query.entity.CompanyInfo;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.lucorda.dflow.data.query.entity.QCompanyInfo.companyInfo;

@Repository
public class CompanyInfoDaoWithQueryDslImpl implements CompanyInfoDaoWithQueryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    public CompanyInfoDaoWithQueryDslImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<CompanyInfo> findByRequestCondition(DataApiRequest dataApiRequest, Pageable pageable) {
        JPAQuery<CompanyInfo> query = jpaQueryFactory.selectFrom(companyInfo)
                .where(companyInfo.registerDate
                        .between(dataApiRequest.getStartDate(), dataApiRequest.getEndDate()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                ;

        if(!CollectionUtils.isEmpty(dataApiRequest.getSectorCodeList())) {
            query = query.where(companyInfo.companyInfoId.sectorCode.in(dataApiRequest.getSectorCodeList()));
        }

        if(!CollectionUtils.isEmpty(dataApiRequest.getDistrictCodeList())) {
            query = query.where(companyInfo.companyInfoId.districtCode.in(dataApiRequest.getDistrictCodeList()));
        }

        if(dataApiRequest.getKeywordToFindCompany() != null) {
            query = query.where(companyInfo.companyName.containsIgnoreCase(dataApiRequest.getKeywordToFindCompany()));
        }

        List<CompanyInfo> companyInfoList = query.fetch();

        return new PageImpl<>(companyInfoList, pageable, companyInfoList.size());
    }
}
