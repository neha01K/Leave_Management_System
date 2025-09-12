package com.lms.dao;

public class SQLQueries {

    public static final String INSERT_EMPLOYEE = "INSERT INTO employees_details(employee_id, name, email, type, joining_date) VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees_details";

    public static final String SELECT_EMPLOYEES_BY_ID = "SELECT * FROM employees_details WHERE employee_id=?";
}
