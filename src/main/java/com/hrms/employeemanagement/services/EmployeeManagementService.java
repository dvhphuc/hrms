package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.dto.*;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.global.paging.PagingInfo;

import java.util.List;

public interface EmployeeManagementService {
	List<Employee> getAllEmployees();
	Employee createEmployee(EmployeeDTO input) throws Exception;
	Employee findEmployee(Integer id);
	EmployeeDetail getEmployeeDetail(Integer id);
	List<Employee> findEmployees(List<Integer> departmentIds);
	List<Employee> findEmployees(Integer departmentId);
	List<Employee> getNewEmployees();
	EmployeePaging filterEmployees(List<Integer> departmentIds,
								   List<Integer> currentContracts,
								   Boolean status,
								   String name,
								   PagingInfo pagingInfo);
	Headcount getHeadcountsStatistic();
	List<HeadcountChartData> getHeadcountChartData();
	Employee updateEmployee(EmployeeDTO input) throws Exception;


}
