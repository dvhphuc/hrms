package com.hrms.employeemanagement.paging;


import com.hrms.employeemanagement.dto.EmployeeSource;

import java.util.List;


public record EmployeePaging(List<EmployeeSource> data, Pagination pagination, long totalCount) {
}
