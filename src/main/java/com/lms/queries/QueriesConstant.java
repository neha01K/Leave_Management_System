package com.lms.queries;

public class QueriesConstant {

    public static String FETCHING_LEAVE_BALANCE =  "SELECT * FROM leaveBalance WHERE employeeId=?";
    public static String FETCHING_EMPLOYEE_LEAVE_HISTORY = "SELECT * FROM leave_history WHERE employeeID=?";
    public static String COUNT_EMPLOYEE = "SELECT COUNT(*) FROM employees";
    public static String REGISTER_EMPLOYEE = "INSERT INTO employees(employeeId, employeeName, designation, email, joiningDate) VALUES(?, ?, ?, ?, ?)";
    public static String INSERT_EMPLOYEE_INTO_LOGIN = "INSERT INTO employees(employeeId, employeeName, designation, email, joiningDate) VALUES(?, ?, ?, ?, ?)";
    public static String LEAVEBALANCE_INITIALIZATION = "INSERT INTO leaveBalance (employeeId) VALUES (?)";
    public static String RETRIEVE_EMPLOYEE_DATA = "SELECT * FROM employees WHERE employeeId = ?";
    public static String ADDING_LEAVE_INTO_HISTORY =  "SELECT * FROM leaveBalance WHERE employeeId=?";
}
