package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.input.DepartmentInComplete;
import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.services.CompetencyCycleService;
import com.hrms.employeecompetency.services.EvaluationOverallService;
import com.hrms.employeecompetency.specifications.EvaluationOverallSpecifications;
import com.hrms.employeemanagement.models.Department;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.DepartmentService;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CompetencyGraphql {
    CompetencyCycleService competencyCycleService;
    DepartmentService departmentService;
    EmployeeService employeeService;
    EvaluationOverallService evaluationOverallService;
    @Autowired
    public CompetencyGraphql(CompetencyCycleService competencyCycleService, DepartmentService departmentService,
                             EmployeeService employeeService, EvaluationOverallService evaluationOverallService) {
        this.competencyCycleService = competencyCycleService;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
        this.evaluationOverallService = evaluationOverallService;
    }

    @QueryMapping(name = "competencyCycles")
    public List<CompetencyCycle> getCompetencyCycles() {
        return competencyCycleService.findAll(Specification.allOf());
    }

    @QueryMapping(name = "departmentInComplete")
    public List<DepartmentInComplete> getAllDepartmentInComplete(@Argument Integer competencyCycleId) {
        List<DepartmentInComplete> departmentInCompletes = new ArrayList<>();
        List<Department> departments = departmentService.findAll(Specification.allOf());
        for (Department department : departments) {
            List<Employee> employees = employeeService.findAll(EmployeeSpecifications.hasDepartmentId(department.getId()));
            float percentage = getPercentageOfEmployees(competencyCycleId, employees);
            DepartmentInComplete departmentInComplete = new DepartmentInComplete(department, percentage);
            departmentInCompletes.add(departmentInComplete);
        }
        return departmentInCompletes;
    }

    @QueryMapping(name = "companyInComplete")
    public Float getCompanyInCompletePercentage(@Argument Integer competencyCycleId) {
        List<Employee> employees = employeeService.findAll(Specification.allOf());
        return getPercentageOfEmployees(competencyCycleId, employees);
    }

    //Get percentage of input employees who has not completed evaluation
    private Float getPercentageOfEmployees(Integer competencyCycleId, List<Employee> employees) {
        int employeeHasCompleted = 0;
        for (Employee employee: employees) {
            if (!evaluationOverallService
                    .findAll(
                            EvaluationOverallSpecifications
                                    .hasEmployeeIdAndStatusCompleted(employee.getId(), competencyCycleId)
                    ).isEmpty()) employeeHasCompleted += 1;
        }
        int employeeHasNotCompleted = employees.size() - employeeHasCompleted;
        return (float) employeeHasNotCompleted / employees.size() * 100;
    }
}
