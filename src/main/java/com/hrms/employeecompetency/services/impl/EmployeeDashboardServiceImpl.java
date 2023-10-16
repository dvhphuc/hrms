package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.dto.CareerPathSummaryDto;
import com.hrms.employeecompetency.dto.GlanceDto;
import com.hrms.employeecompetency.dto.OverallCompetencyScoreDto;
import com.hrms.employeecompetency.dto.SkillDto;
import com.hrms.employeecompetency.services.EmployeeDashboardService;

import java.util.List;

public class EmployeeDashboardServiceImpl implements EmployeeDashboardService {
    @Override
    public CareerPathSummaryDto getCareerPathSummary(Integer employeeId) {
        return null;
    }

    @Override
    public GlanceDto getGlance(Integer employeeId) {
        return null;
    }

    @Override
    public OverallCompetencyScoreDto getOverallCompetencyScore(Integer employeeId) {
        return null;
    }

    @Override
    public List<SkillDto> getTopSkills(Integer employeeId) {
        return null;
    }

    @Override
    public List<SkillDto> getTopInterest(Integer employeeId) {
        return null;
    }

    @Override
    public List<SkillDto> getImprovements(Integer employeeId) {
        return null;
    }
}
