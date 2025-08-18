package com.lms.exceptions;

public class EmployeeNotFound extends LMSException{
    public EmployeeNotFound(String employeeId) {
        super("EMP_404", "Employee not found with ID: " + employeeId);
    }

    public EmployeeNotFound(String message, Throwable cause) {
        super("EMP_404", message, cause);
    }
}
