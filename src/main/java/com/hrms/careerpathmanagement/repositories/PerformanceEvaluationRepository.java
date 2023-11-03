package com.hrms.careerpathmanagement.repositories;

import com.hrms.performancemanagement.model.PerformanceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PerformanceEvaluationRepository extends JpaRepository<PerformanceEvaluation, Integer> , JpaSpecificationExecutor<PerformanceEvaluation> {


    @Query("SELECT pe, e, p " +
            "FROM PerformanceEvaluation pe " +
            "INNER JOIN PerformanceCycle pc ON pe.performanceCycle.performanceCycleId = pc.performanceCycleId " +
            "INNER JOIN Employee e ON pe.employee.id = e.id " +
            "INNER JOIN Position p ON e.position.id = p.id " +
            "WHERE p.id = ?1 AND pc.performanceCycleId = ?2")

    List<PerformanceEvaluation> findByCycleIdAndPositionId(Integer positionId, Integer cycleId);
}