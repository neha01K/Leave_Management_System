package com.lms.dao;

public class SQLQueries {

    public static final String INSERT_EMPLOYEE = "INSERT INTO employees_details(employee_id, name, email, type, joining_date, manager_id, password_hash) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees_details";

    public static final String SELECT_EMPLOYEES_BY_ID = "SELECT * FROM employees_details WHERE employee_id=?";

    public static final String SELECT_EMPLOYEES_BY_EMAIL = "SELECT * FROM employees_details WHERE email=?";

    public static final String SELECT_FIRST_EMPLOYEE_ID_BY_TYPE = "SELECT employee_id FROM employees_details WHERE type=? ORDER BY joining_date ASC LIMIT 1";

    public static final String INSERT_LEAVE_REQUEST = "INSERT INTO leave_requests(leave_request_id, employee_id, leave_type, start_date, end_date, number_of_days, reason, status, approved_by, request_date, medical_certificate, parenthood_certificate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SELECT_LEAVE_REQUESTS_BY_EMPLOYEE = "SELECT * FROM leave_requests WHERE employee_id=? ORDER BY request_date DESC";

    public static final String SELECT_PENDING_LEAVE_REQUESTS = "SELECT * FROM leave_requests WHERE status='PENDING' ORDER BY request_date ASC";

    public static final String UPDATE_LEAVE_REQUEST_STATUS = "UPDATE leave_requests SET status=?, approved_by=? WHERE leave_request_id=?";

    public static final String UPSERT_LEAVE_BALANCE = "INSERT INTO employee_leave_balances(employee_id, leave_type, available_days, used_days) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE available_days=VALUES(available_days), used_days=VALUES(used_days)";

    public static final String SELECT_LEAVE_BALANCES_BY_EMPLOYEE = "SELECT leave_type, available_days FROM employee_leave_balances WHERE employee_id=?";

    public static final String DEDUCT_LEAVE_BALANCE = "UPDATE employee_leave_balances SET available_days = available_days - ?, used_days = used_days + ? WHERE employee_id=? AND leave_type=?";
}
