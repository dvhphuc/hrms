package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Integer>, JpaSpecificationExecutor<EmergencyContact> {
}
