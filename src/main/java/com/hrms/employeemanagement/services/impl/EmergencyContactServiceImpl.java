package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.models.EmergencyContact;
import com.hrms.employeemanagement.repositories.EmergencyContactRepository;
import com.hrms.employeemanagement.services.EmergencyContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmergencyContactServiceImpl implements EmergencyContactService {
    @Autowired
    EmergencyContactRepository emergencyContactRepository;
    @Override
    public EmergencyContact save(EmergencyContact emergencyContact) {
        return emergencyContactRepository.save(emergencyContact);
    }
}
