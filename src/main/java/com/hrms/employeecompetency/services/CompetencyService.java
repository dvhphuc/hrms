package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.dto.EmployeeRating;

import java.util.List;

public interface CompetencyService {
    List<EmployeeRating> findAllByCompetencyCycleId(Integer competencyCycleId);
}
