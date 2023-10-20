package com.hrms.employeecompetency.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrms.employeecompetency.dto.EmployeeOverviewDto;
import com.hrms.employeemanagement.models.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapperService {
    private ModelMapper employeeMapper = new ModelMapper();

//    @Bean
//    void initEmployeeMapper() {
//        employeeMapper.typeMap(Employee.class, EmployeeOverviewDto.class).addMappings(mapper -> {
//            mapper.map(Employee::getEmployeeSkills, EmployeeOverviewDto::setSkills);
//            mapper.map(Employee::getFirstName, EmployeeOverviewDto::setFirstName);
//            mapper.map(Employee::getLastName, EmployeeOverviewDto::setLastName);
//            mapper.map(Employee::getId, EmployeeOverviewDto::setEmployeeId);
//            mapper.map(Employee::getAddress, EmployeeOverviewDto::setAddress);
//            mapper.map(Employee::getPositionLevel, EmployeeOverviewDto::setPositionLevel);
//        });
//    }

    public EmployeeOverviewDto employeeOverview(Employee employee) {
        return employeeMapper.map(employee, EmployeeOverviewDto.class);
    }
}
