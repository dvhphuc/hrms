package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.services.CompetencyCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CompetencyGraphql {
    CompetencyCycleService competencyCycleService;
    @Autowired
    public CompetencyGraphql(CompetencyCycleService competencyCycleService) {
        this.competencyCycleService = competencyCycleService;
    }

    @QueryMapping(name = "competencyCycles")
    public List<CompetencyCycle> getCompetencyCycles() {
        return competencyCycleService.findAll(Specification.allOf());
    }
}
