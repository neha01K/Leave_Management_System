USE leave_management_system_db;

ALTER TABLE employees_details
    ADD COLUMN manager_id VARCHAR(20) NULL,
    ADD COLUMN password_hash VARCHAR(100) NULL;

UPDATE employees_details
SET password_hash = '$2a$12$uyYQm3M7feZTkGNLfOJjHOx4ryAz9O/nw/4wF4vtuxdeLO/Gb2uZC'
WHERE password_hash IS NULL OR password_hash = '';

ALTER TABLE employees_details
    MODIFY password_hash VARCHAR(100) NOT NULL;

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

INSERT INTO employee_leave_balances(employee_id, leave_type, available_days, used_days)
SELECT employee_id, leave_type, available_days, 0
FROM employees_details
CROSS JOIN (
    SELECT 'CASUAL_LEAVE' AS leave_type, 10 AS available_days
    UNION ALL SELECT 'EARNED_LEAVE', 0
    UNION ALL SELECT 'SICK_LEAVE', 12
    UNION ALL SELECT 'DUTY_LEAVE', 0
    UNION ALL SELECT 'MATERNITY_LEAVE', 120
    UNION ALL SELECT 'PARENTAL_LEAVE', 7
    UNION ALL SELECT 'LEAVE_WITHOUT_PAY', 180
) balances
ON DUPLICATE KEY UPDATE employee_id = employee_id;
