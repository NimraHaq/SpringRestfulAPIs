package com.restful.employees.service;

import com.restful.employees.dao.EmployeeRepository;
import com.restful.employees.dto.EmployeeDto;
import com.restful.employees.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Employee addEmployee(EmployeeDto employeeDto) {
        return employeeRepository.save(mapDtoToEntity(0, employeeDto));
    }

    @Transactional
    @Override
    public Employee updateEmployee(long id, EmployeeDto employeeDto) {
        return employeeRepository.save(mapDtoToEntity(id, employeeDto));
    }

    @Transactional
    @Override
    public void deleteEmployeeById(long id) {
        employeeRepository.deleteById(id);
    }

    private Employee mapDtoToEntity(long id, EmployeeDto employeeDto){
        return new Employee(id, employeeDto.getFirstName(), employeeDto.getLastName(), employeeDto.getEmail());
    }
}
