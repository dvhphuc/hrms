package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.CompetencyTimeLine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompetencyTimeLineRepository extends JpaRepository<CompetencyTimeLine, Integer>, JpaSpecificationExecutor<CompetencyTimeLine> {
    //check if current date is after due date set isDone to true
    @Modifying
    @Transactional
    @Query("UPDATE CompetencyTimeLine ctl SET ctl.isDone = true WHERE ctl.dueDate < current_date")
    void updateIsDoneForOverdueItems();
}
