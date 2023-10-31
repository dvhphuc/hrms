package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> , JpaSpecificationExecutor<Employee> {
    @Modifying
    @Query(value = "INSERT INTO hrms.employee (employee_id, last_name, first_name, email, joined_date, gender, phone_number, address, department_id, job_level_id, position_id, current_contract, profile_bio, facebook_link, instagram_link, linkedin_link, twitter_link, date_of_birth) " +
            "VALUES (:employeeId, :lastName, :firstName, :email, :joinedDate, :gender, :phoneNumber, :address, :departmentId, :jobLevelId, :positionId, :currentContract, :profileBio, :facebookLink, :instagramLink, :linkedinLink, :twitterLink, :dateOfBirth)",
            nativeQuery = true)
    void insertEmployee(
            Integer employeeId, String lastName, String firstName, String email, Date joinedDate,
            String gender, String phoneNumber, String address, Integer departmentId, Integer jobLevelId,
            Integer positionId, Integer currentContract, String profileBio, String facebookLink,
            String instagramLink, String linkedinLink, String twitterLink, Date dateOfBirth);

}
