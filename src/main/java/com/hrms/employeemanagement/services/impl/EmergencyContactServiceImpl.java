package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.models.EmergencyContact;
import com.hrms.employeemanagement.repositories.EmergencyContactRepository;
import com.hrms.employeemanagement.services.EmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmergencyContactServiceImpl implements EmergencyContactService {
    @Autowired
    EmergencyContactRepository emergencyContactRepository;

    @Override
    public List<EmergencyContact> findAll(Specification<EmergencyContact> spec) {
        return emergencyContactRepository.findAll(spec);
    }

    @Override
    public List<EmergencyContact> findAll(Specification<EmergencyContact> spec, Sort sort) {
        return emergencyContactRepository.findAll(spec, sort);
    }

    @Override
    public Page<EmergencyContact> findAll(Specification<EmergencyContact> spec, Pageable pageable) {
        return emergencyContactRepository.findAll(spec, pageable);
    }

    @Override
    public EmergencyContact save(EmergencyContact emergencyContact) {
        return emergencyContactRepository.save(emergencyContact);
    }
}
