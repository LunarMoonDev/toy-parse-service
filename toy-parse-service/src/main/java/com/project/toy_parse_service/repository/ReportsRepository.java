package com.project.toy_parse_service.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import com.project.toy_parse_service.entity.Reports;

@Repository
@Transactional
public interface ReportsRepository extends JpaRepository<Reports, BigInteger> {
}
