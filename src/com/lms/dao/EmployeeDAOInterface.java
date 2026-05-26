package com.lms.dao;

import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;

import java.util.List;

public interface EmployeeDAOInterface {

     void saveEmployee(Employee employee);

     List<Employee> getAllEmployeesDetails();

     Employee getEmployeeDetailByEmployeeID(String employeeID);

     Employee getEmployeeDetailByEmail(String email);

     String findFirstEmployeeIDByType(EmployeeType employeeType);

}
