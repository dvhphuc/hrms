package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.Project;

import java.util.Optional;

public interface ProjectService {
    Iterable<Project> getAllProjects();
    Project saveProject(Project project);
    Optional<Project> getProjectById(int id);
    Optional<Project> updateProject(int id, Project project);
    void deleteProjectById(int id);
}
