package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.EmergencyContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EmergencyContactService {
    List<EmergencyContact> findAll(Specification<EmergencyContact> spec);
    List<EmergencyContact> findAll(Specification<EmergencyContact> spec, Sort sort);
    Page<EmergencyContact> findAll(Specification<EmergencyContact> spec, Pageable pageable);
    EmergencyContact save(EmergencyContact emergencyContact);
}
