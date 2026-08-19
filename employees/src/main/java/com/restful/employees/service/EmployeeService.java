package com.restful.employees.service;

import com.restful.employees.dto.EmployeeDto;
import com.restful.employees.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(long id);
    Employee addEmployee(EmployeeDto employee);
    Employee updateEmployee(long id, EmployeeDto employee);
    void deleteEmployeeById(long id);
}
