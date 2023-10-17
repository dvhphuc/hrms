package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.dto.*;

import java.util.List;

public interface EmployeeDashboardService {
    CareerPathSummaryDto getCareerPathSummary(Integer employeeId);
    GlanceDto getGlance(Integer employeeId);
    RadarChart getOverallCompetencyScore(Integer employeeId);
    List<SkillDto> getTopSkills(Integer employeeId);
    List<SkillDto> getTopInterest(Integer employeeId);
    List<SkillDto> getImprovements(Integer employeeId);

}
