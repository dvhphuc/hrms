package com.hrms.careerpathmanagement.models;

import com.hrms.employeemanagement.models.JobLevel;
import com.hrms.employeemanagement.models.Position;
import com.hrms.employeemanagement.models.SkillSet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PositionJobLevelSkillSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position;

    @ManyToOne
    @JoinColumn(name = "job_level_id", referencedColumnName = "job_level_id")
    private JobLevel jobLevel;

    @ManyToOne
    @JoinColumn(name = "skill_set_id", referencedColumnName = "skill_set_id")
    private SkillSet skillSet;

    @ManyToOne
    @JoinColumn(name = "proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel proficiencyLevel;
}
