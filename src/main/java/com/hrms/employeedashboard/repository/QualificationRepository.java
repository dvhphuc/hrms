package com.hrms.employeedashboard.repository;

import com.hrms.employeecompetency.models.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {
    List<Qualification> findAllByEmployeeId(Integer employeeId);
}
