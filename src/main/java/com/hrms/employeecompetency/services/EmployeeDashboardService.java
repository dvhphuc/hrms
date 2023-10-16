package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.dto.CareerPathSummaryDto;
import com.hrms.employeecompetency.dto.GlanceDto;
import com.hrms.employeecompetency.dto.OverallCompetencyScoreDto;
import com.hrms.employeecompetency.dto.SkillDto;

import java.util.List;

public interface EmployeeDashboardService {
    CareerPathSummaryDto getCareerPathSummary(Integer employeeId);
    GlanceDto getGlance(Integer employeeId);
    OverallCompetencyScoreDto getOverallCompetencyScore(Integer employeeId);
    List<SkillDto> getTopSkills(Integer employeeId);
    List<SkillDto> getTopInterest(Integer employeeId);
    List<SkillDto> getImprovements(Integer employeeId);

}
