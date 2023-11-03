package com.hrms.careerpathmanagement.mapper;

import com.hrms.careerpathmanagement.dto.EmployeeOverviewDTO;
import com.hrms.employeemanagement.models.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapperService {
    private ModelMapper employeeMapper = new ModelMapper();

//    @Bean
//    void initEmployeeMapper() {
//        employeeMapper.typeMap(Employee.class, EmployeeOverviewDTO.class).addMappings(mapper -> {
//            mapper.map(Employee::getEmployeeSkills, EmployeeOverviewDTO::setSkills);
//            mapper.map(Employee::getFirstName, EmployeeOverviewDTO::setFirstName);
//            mapper.map(Employee::getLastName, EmployeeOverviewDTO::setLastName);
//            mapper.map(Employee::getId, EmployeeOverviewDTO::setEmployeeId);
//            mapper.map(Employee::getAddress, EmployeeOverviewDTO::setAddress);
//            mapper.map(Employee::getPositionLevel, EmployeeOverviewDTO::setPositionLevel);
//        });
//    }

    public EmployeeOverviewDTO employeeOverview(Employee employee) {
        return employeeMapper.map(employee, EmployeeOverviewDTO.class);
    }
}
