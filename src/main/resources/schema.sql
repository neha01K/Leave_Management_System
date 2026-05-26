CREATE DATABASE IF NOT EXISTS leave_management_system_db;
USE leave_management_system_db;

CREATE TABLE IF NOT EXISTS employees_details (
    employee_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    type VARCHAR(20) NOT NULL,
    joining_date DATE NOT NULL,
    manager_id VARCHAR(20),
    password_hash VARCHAR(64) NOT NULL,
    CONSTRAINT fk_employee_manager
        FOREIGN KEY (manager_id) REFERENCES employees_details(employee_id)
        ON DELETE SET NULL
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
     '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
    ('EMP1002', 'Demo Lead', 'lead@example.com', 'LEAD', '2022-02-01', 'EMP1001',
     '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
    ('EMP1003', 'Demo Executive', 'executive@example.com', 'EXECUTIVE', '2022-03-01', 'EMP1002',
     '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8')
ON DUPLICATE KEY UPDATE employee_id = employee_id;
