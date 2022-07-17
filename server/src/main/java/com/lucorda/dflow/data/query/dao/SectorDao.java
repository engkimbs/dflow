package com.lucorda.dflow.data.query.dao;

import com.lucorda.dflow.data.query.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectorDao extends JpaRepository<Sector, Long> {
}
