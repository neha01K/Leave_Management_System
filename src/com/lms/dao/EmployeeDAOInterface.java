package com.lms.dao;

import com.lms.models.Employee;

import java.util.List;

public interface EmployeeDAOInterface {

     void saveEmployee(Employee employee);

     List<Employee> getAllEmployeesDetails();

     Employee getEmployeeDetailByEmployeeID(String employeeID);


}
