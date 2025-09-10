package com.lms.exceptions;

public class EmployeeNotFound extends LMSException{
    public EmployeeNotFound(String employeeId) {
        super("Employee not found with ID: " + employeeId);
    }

    public EmployeeNotFound(String message, Throwable cause) {
        super(message, cause);
        System.out.println("No Employee found with these details!");
    }
}
