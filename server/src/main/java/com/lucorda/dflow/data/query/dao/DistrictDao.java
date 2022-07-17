package com.lucorda.dflow.data.query.dao;

import com.lucorda.dflow.data.query.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictDao extends JpaRepository<District, Long> {
}
