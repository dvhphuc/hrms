package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.EvaluationOverall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EvaluationOverallRepository extends JpaRepository<EvaluationOverall, Integer>, JpaSpecificationExecutor<EvaluationOverall> {
}
