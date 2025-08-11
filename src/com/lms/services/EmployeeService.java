package com.lms.services;

import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    private Map<String, Employee> employees;

    public EmployeeService() {
        employees = new HashMap<>();
        addDefaultEmployees();
    }

    private void addDefaultEmployees() {
        Employee manager = new Employee("John Manager", "manager@company.com", EmployeeType.MANAGER, LocalDate.of(2020, 1, 1));
        employees.put(manager.getEmployeeId(), manager);

        Employee lead = new Employee("Jane Lead", "lead@company.com", EmployeeType.LEAD, LocalDate.of(2021, 1, 1));
        lead.setManagerId(manager.getEmployeeId());
        employees.put(lead.getEmployeeId(), lead);

        Employee executive = new Employee("Bob Executive", "exec@company.com", EmployeeType.EXECUTIVE, LocalDate.of(2022, 1, 1));
        executive.setManagerId(lead.getEmployeeId());
        employees.put(executive.getEmployeeId(), executive);

        System.out.println("Default employees created:");
        System.out.println("Manager ID: " + manager.getEmployeeId());
        System.out.println("Lead ID: " + lead.getEmployeeId());
        System.out.println("Executive ID: " + executive.getEmployeeId());
    }

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

    public Employee getEmployee(String employeeId) {
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
