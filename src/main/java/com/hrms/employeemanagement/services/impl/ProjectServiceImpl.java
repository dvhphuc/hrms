package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.exception.ProjectNotFoundException;
import com.hrms.employeemanagement.models.Project;
import com.hrms.employeemanagement.repositories.ProjectRepository;
import com.hrms.employeemanagement.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Iterable<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProjectById(int id) throws ProjectNotFoundException {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException("Project not found for id :: " + id);
        }
        return project;
    }

    @Override
    public Optional<Project> updateProject(int id, Project project) throws ProjectNotFoundException {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isEmpty())
            throw new ProjectNotFoundException("Project not found for id :: " + id);
        project.setId(id);
        projectRepository.save(project);
        return projectOptional;
    }

    @Override
    public void deleteProjectById(int id) {
        projectRepository.deleteById(id);
    }
}
