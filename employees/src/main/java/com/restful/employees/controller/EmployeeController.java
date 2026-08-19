package com.restful.employees.controller;


import com.restful.employees.dto.EmployeeDto;
import com.restful.employees.entity.Employee;
import com.restful.employees.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee Rest API", description = "Operations related to employees.")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Get employee by ID", description = "Retrieve an employee by their ID.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/getEmployeeById/{id}")
    public Employee getEmployeeById(@PathVariable @Min(value = 1) long id){
        return employeeService.getEmployeeById(id);
    }

    @Operation(summary = "Add a new employee", description = "Add a new employee to the system.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/addEmployee")
    public void addEmployee(@Valid @RequestBody EmployeeDto employeeDto){
        employeeService.addEmployee(employeeDto);
    }

    @Operation(summary = "Update an employee", description = "Update an existing employee's information.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/updateEmployee/{id}")
    public void updateEmployee(@PathVariable @Min(1) long id, @Valid @RequestBody EmployeeDto employeeDto){
        employeeService.updateEmployee(id, employeeDto);
    }

    @Operation(summary = "Delete an employee", description = "Delete an employee by their ID.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/deleteEmployee/{id}")
    public void deleteEmployeeById(@PathVariable @Min(1) long id){
        employeeService.deleteEmployeeById(id);
    }
}
