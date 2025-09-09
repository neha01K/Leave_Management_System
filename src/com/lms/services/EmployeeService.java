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
    public Employee createEmployee(String employeeName, String employeeEmail, EmployeeType employeeType, LocalDate employeeJoiningDate) {
        Employee newEmployee = new Employee(employeeName, employeeEmail, employeeType, employeeJoiningDate);

        if (employeeType == EmployeeType.EXECUTIVE) {
            String leadID = findFirstLeadID();
            if (leadID != null) {
                newEmployee.setManagerID(leadID);
            }
        } else if (employeeType == EmployeeType.LEAD) {
            String managerID = findFirstManagerID();
            if (managerID != null) {
                newEmployee.setManagerID(managerID);
            }
        }
        if(newEmployee.getEmployeeType()==null){
            throw new NullPointerException("Employee Type can't be Null");
        }

        employees.put(newEmployee.getEmployeeID(), newEmployee);
        return newEmployee;
    }

    public Employee getEmployee(String employeeID) throws EmployeeNotFound{
        if(employeeID==null || employeeID.trim().isEmpty()){
            throw new EmployeeNotFound("Employee ID cannot be null or empty");
        }

        Employee employee = employees.get(employeeID);
        if(employee == null){
            throw new EmployeeNotFound(employeeID);
        }
        return employees.get(employeeID);
    }

    public Map<String, Employee> getAllEmployees() {
        return employees;
    }

    private String findFirstLeadID() {
        for (Employee employee : employees.values()) {
            if (employee.getEmployeeType() == EmployeeType.LEAD) {
                return employee.getEmployeeID();
            }
        }
        return null;
    }

    private String findFirstManagerID() {
        for (Employee employee : employees.values()) {
            if (employee.getEmployeeType() == EmployeeType.MANAGER) {
                return employee.getEmployeeID();
            }
        }
        return null;
    }
}
