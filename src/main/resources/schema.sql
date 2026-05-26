CREATE DATABASE IF NOT EXISTS leave_management_system_db;
USE leave_management_system_db;

CREATE TABLE IF NOT EXISTS employees_details (
    employee_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    joining_date DATE NOT NULL,
    manager_id VARCHAR(20),
    password_hash VARCHAR(100) NOT NULL,
    CONSTRAINT fk_employee_manager
        FOREIGN KEY (manager_id) REFERENCES employees_details(employee_id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS employee_leave_balances (
    employee_id VARCHAR(20) NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    available_days INT NOT NULL,
    used_days INT NOT NULL DEFAULT 0,
    PRIMARY KEY (employee_id, leave_type),
    CONSTRAINT fk_balance_employee
        FOREIGN KEY (employee_id) REFERENCES employees_details(employee_id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS leave_requests (
    leave_request_id VARCHAR(20) PRIMARY KEY,
    employee_id VARCHAR(20) NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    number_of_days INT NOT NULL,
    reason VARCHAR(500),
    status VARCHAR(20) NOT NULL,
    approved_by VARCHAR(20),
    request_date DATE NOT NULL,
    medical_certificate VARCHAR(255),
    parenthood_certificate VARCHAR(255),
    CONSTRAINT fk_leave_employee
        FOREIGN KEY (employee_id) REFERENCES employees_details(employee_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_leave_approver
        FOREIGN KEY (approved_by) REFERENCES employees_details(employee_id)
        ON DELETE SET NULL
);

INSERT INTO employees_details(employee_id, name, email, type, joining_date, manager_id, password_hash)
VALUES
    ('EMP1001', 'Demo Manager', 'manager@example.com', 'MANAGER', '2022-01-01', NULL,
     '$2a$12$uyYQm3M7feZTkGNLfOJjHOx4ryAz9O/nw/4wF4vtuxdeLO/Gb2uZC'),
    ('EMP1002', 'Demo Lead', 'lead@example.com', 'LEAD', '2022-02-01', 'EMP1001',
     '$2a$12$uyYQm3M7feZTkGNLfOJjHOx4ryAz9O/nw/4wF4vtuxdeLO/Gb2uZC'),
    ('EMP1003', 'Demo Executive', 'executive@example.com', 'EXECUTIVE', '2022-03-01', 'EMP1002',
     '$2a$12$uyYQm3M7feZTkGNLfOJjHOx4ryAz9O/nw/4wF4vtuxdeLO/Gb2uZC')
ON DUPLICATE KEY UPDATE employee_id = employee_id;

INSERT INTO employee_leave_balances(employee_id, leave_type, available_days, used_days)
VALUES
    ('EMP1001', 'CASUAL_LEAVE', 10, 0),
    ('EMP1001', 'EARNED_LEAVE', 0, 0),
    ('EMP1001', 'SICK_LEAVE', 12, 0),
    ('EMP1001', 'DUTY_LEAVE', 0, 0),
    ('EMP1001', 'MATERNITY_LEAVE', 120, 0),
    ('EMP1001', 'PARENTAL_LEAVE', 7, 0),
    ('EMP1001', 'LEAVE_WITHOUT_PAY', 180, 0),
    ('EMP1002', 'CASUAL_LEAVE', 10, 0),
    ('EMP1002', 'EARNED_LEAVE', 0, 0),
    ('EMP1002', 'SICK_LEAVE', 12, 0),
    ('EMP1002', 'DUTY_LEAVE', 0, 0),
    ('EMP1002', 'MATERNITY_LEAVE', 120, 0),
    ('EMP1002', 'PARENTAL_LEAVE', 7, 0),
    ('EMP1002', 'LEAVE_WITHOUT_PAY', 180, 0),
    ('EMP1003', 'CASUAL_LEAVE', 10, 0),
    ('EMP1003', 'EARNED_LEAVE', 0, 0),
    ('EMP1003', 'SICK_LEAVE', 12, 0),
    ('EMP1003', 'DUTY_LEAVE', 0, 0),
    ('EMP1003', 'MATERNITY_LEAVE', 120, 0),
    ('EMP1003', 'PARENTAL_LEAVE', 7, 0),
    ('EMP1003', 'LEAVE_WITHOUT_PAY', 180, 0)
ON DUPLICATE KEY UPDATE employee_id = employee_id;
