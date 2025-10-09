package com.lms.queries;

public class QueriesConstantForRegister {

    public static String COUNT_EMPLOYEE = "SELECT COUNT(*) FROM employees";
    public static String REGISTER_EMPLOYEE = "INSERT INTO employees(employeeId, employeeName, designation, email, joiningDate) VALUES(?, ?, ?, ?, ?)";
    public static String INSERT_EMPLOYEE_INTO_LOGIN = "INSERT INTO employees(employeeId, employeeName, designation, email, joiningDate) VALUES(?, ?, ?, ?, ?)";
    public static String LEAVEBALANCE_INITIALIZATION = "INSERT INTO leaveBalance (employeeId) VALUES (?)";
    public static String RETRIEVE_EMPLOYEE_DATA = "SELECT * FROM employees WHERE employeeId = ?";
}
