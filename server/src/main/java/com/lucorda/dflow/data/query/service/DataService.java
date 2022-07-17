package com.lucorda.dflow.data.query.service;

import com.lucorda.dflow.data.api.dto.DataApiRequest;
import com.lucorda.dflow.data.query.dao.CompanyInfoDao;
import com.lucorda.dflow.data.query.dao.DistrictDao;
import com.lucorda.dflow.data.query.dao.SectorDao;
import com.lucorda.dflow.data.query.entity.CompanyInfo;
import com.lucorda.dflow.data.query.entity.District;
import com.lucorda.dflow.data.query.entity.Sector;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DataService {

    final SectorDao sectorDao;

    final DistrictDao districtDao;

    final CompanyInfoDao companyInfoDao;

    public DataService(SectorDao sectorDao, DistrictDao districtDao, CompanyInfoDao companyInfoDao) {
        this.sectorDao = sectorDao;
        this.districtDao = districtDao;
        this.companyInfoDao = companyInfoDao;
    }

    public List<Sector> findAllSectors() {
        return sectorDao.findAll();
    }

    public List<District> findAllDistricts() {
        return districtDao.findAll();
    }

    public Page<CompanyInfo> findCompanyInfoWithRequestCondition(DataApiRequest dataApiRequest, Pageable pageable) {
        return companyInfoDao.findByRequestCondition(dataApiRequest, pageable);
    }
}
