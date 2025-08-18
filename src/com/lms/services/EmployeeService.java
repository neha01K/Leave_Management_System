package com.lms.services;

import com.lms.exceptions.EmployeeNotFound;
import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    private Map<String, Employee> employees;

    public EmployeeService() {
        employees = new HashMap<>();
    }

    // Create a new employee and assign manager if needed
    public Employee createEmployee(String name, String email, EmployeeType type, LocalDate joiningDate) {
        Employee newEmployee = new Employee(name, email, type, joiningDate);

        if (type == EmployeeType.EXECUTIVE) {
            String leadId = findFirstLeadId();
            if (leadId != null) {
                newEmployee.setManagerId(leadId);
            }
        } else if (type == EmployeeType.LEAD) {
            String managerId = findFirstManagerId();
            if (managerId != null) {
                newEmployee.setManagerId(managerId);
            }
        }

        employees.put(newEmployee.getEmployeeId(), newEmployee);
        return newEmployee;
    }

    public Employee getEmployee(String employeeId) throws EmployeeNotFound{
        if(employeeId==null || employeeId.trim().isEmpty()){
            throw new EmployeeNotFound("Employee ID cannot be null or empty");
        }

        Employee employee = employees.get(employeeId);
        if(employee == null){
            throw new EmployeeNotFound(employeeId);
        }
        return employees.get(employeeId);
    }

    public Map<String, Employee> getAllEmployees() {
        return employees;
    }

    private String findFirstLeadId() {
        for (Employee employee : employees.values()) {
            if (employee.getType() == EmployeeType.LEAD) {
                return employee.getEmployeeId();
            }
        }
        return null;
    }

    private String findFirstManagerId() {
        for (Employee employee : employees.values()) {
            if (employee.getType() == EmployeeType.MANAGER) {
                return employee.getEmployeeId();
            }
        }
        return null;
    }
}
