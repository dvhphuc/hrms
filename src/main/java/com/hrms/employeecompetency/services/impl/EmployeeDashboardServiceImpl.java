package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.dto.*;
import com.hrms.employeecompetency.models.Competency;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.repositories.CompetencyEvaluationRepository;
import com.hrms.employeecompetency.repositories.CompetencyRepository;
import com.hrms.employeecompetency.repositories.EmployeeSkillRepository;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import com.hrms.employeecompetency.services.CompetencyService;
import com.hrms.employeecompetency.services.EmployeeDashboardService;
import com.hrms.employeemanagement.models.EmployeeSkill;
import com.hrms.employeemanagement.models.Position;
import com.hrms.employeemanagement.models.PositionLevel;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDashboardServiceImpl implements EmployeeDashboardService {
    @Autowired
    CompetencyEvaluationRepository competencyEvaluationRepository;

    @Autowired
    EmployeeSkillRepository employeeSkillRepository;

    @Autowired
    CompetencyRepository competencyRepository;

    @Override
    public CareerPathSummaryDto getCareerPathSummary(Integer employeeId) {
        return null;
    }

    @Override
    public GlanceDto getGlance(Integer employeeId) {
        Specification<EmployeeSkill> spec = Specification.where(null);
        spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), employeeId));
        List<EmployeeSkill> employeeSkills = employeeSkillRepository.findAll(spec);
        var totalSkills = employeeSkills.size();
        var skillScore = employeeSkills.stream().mapToInt(employeeSkill -> employeeSkill.getProficiencyLevel().getScore()).sum();
        Float currentSkillGap = (float) (skillScore / totalSkills);


        Specification<CompetencyEvaluation> ceSpec = Specification.where(null);
        ceSpec = ceSpec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), employeeId));
        var competencyLevel = competencyEvaluationRepository.findAll(ceSpec).stream().mapToInt(competencyEvaluation -> competencyEvaluation.getFinalScore().getScore()).sum();

        return new GlanceDto(currentSkillGap, 0.4F, (float) competencyLevel);

    }

    @Override
    public RadarChart getOverallCompetencyScore(Integer employeeId) {
        var labels = competencyRepository.findAll().stream().map(Competency::getCompetencyName).toList();
        var data = competencyEvaluationRepository.findAll().stream().map(competencyEvaluation -> competencyEvaluation.getFinalScore().getScore()*1.0).toList();
        //return new RadarChart(labels, List.of(new RadarDataset("line example", data)));
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
