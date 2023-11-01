package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.CompetencyCycle;
import com.hrms.careerpathmanagement.models.EvaluationOverall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface EvaluationOverallRepository extends JpaRepository<EvaluationOverall, Integer>, JpaSpecificationExecutor<EvaluationOverall> {
    @Query("SELECT s.competencyCycle FROM EvaluationOverall s WHERE s.employee.id = ?1 AND s.finalStatus = 'Agreed' ORDER BY s.competencyCycle.startDate DESC LIMIT 1")
    CompetencyCycle latestEvalCompetencyCycle(Integer employeeId);
}
