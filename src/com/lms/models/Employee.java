package com.lms.models;

import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Employee {
    private String employeeID;
    private String employeeName;
    private String employeeEmail;
    private EmployeeType employeeType;
    private String managerID;
    private LocalDate employeeJoiningDate;
    private Map<LeaveType, Integer> employeeLeaveBalance;
    private Map<LeaveType, Integer> employeeUsedLeaves;
    private int maternityLeavesUsed = 0;
    private int parentalLeavesUsed = 0;

    public Employee(String employeeName, String employeeEmail, EmployeeType employeeType, LocalDate employeeJoiningDate) {
        this.employeeID = generateEmployeeID();
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeType = employeeType;
        this.employeeJoiningDate = employeeJoiningDate;
        this.employeeLeaveBalance = new HashMap<>();
        this.employeeUsedLeaves = new HashMap<>();
        initializeLeaveBalance();
    }

    public Employee(String employeeID, String employeeName, String employeeEmail, EmployeeType employeeType, LocalDate employeeJoiningDate) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.employeeType = employeeType;
        this.employeeJoiningDate = employeeJoiningDate;
        this.employeeLeaveBalance = new HashMap<>();
        this.employeeUsedLeaves = new HashMap<>();
        initializeLeaveBalance();
    }


    private String generateEmployeeID() {
        return "EMP" + System.currentTimeMillis() % 10000;
    }

    private void initializeLeaveBalance() {
        LocalDate currentDate = LocalDate.now();
        int monthsWorked = (int) ChronoUnit.MONTHS.between(
                LocalDate.of(currentDate.getYear(), 1, 1),
                employeeJoiningDate.isAfter(LocalDate.of(currentDate.getYear(), 1, 1)) ?
                        employeeJoiningDate : LocalDate.of(currentDate.getYear(), 1, 1)
        );

        for (LeaveType type : LeaveType.values()) {
            int allocation = type.getYearlyAllocation();

            if ((type == LeaveType.CASUAL_LEAVE || type == LeaveType.SICK_LEAVE) &&
                    employeeJoiningDate.getYear() == currentDate.getYear() && monthsWorked > 0) {
                allocation = (allocation * (12 - monthsWorked)) / 12;
            }

            employeeLeaveBalance.put(type, allocation);
            employeeUsedLeaves.put(type, 0);
        }

        if (employeeJoiningDate.getYear() == currentDate.getYear()) {
            int monthsCompleted = (int) ChronoUnit.MONTHS.between(employeeJoiningDate, currentDate);
            employeeLeaveBalance.put(LeaveType.EARNED_LEAVE, (int) (monthsCompleted * 1.25));
        }
    }

    // Getters and Setters
    public String getEmployeeID() { return employeeID; }
    public String getEmployeeName() { return employeeName; }
    public String getEmployeeEmail() { return employeeEmail; }
    public EmployeeType getEmployeeType() { return employeeType; }
    public String getManagerID() { return managerID; }
    public void setManagerID(String managerId) { this.managerID = managerId; }
    public LocalDate getEmployeeJoiningDate() { return employeeJoiningDate; }
    public Map<LeaveType, Integer> getEmployeeLeaveBalance() { return employeeLeaveBalance; }
    public Map<LeaveType, Integer> getEmployeeUsedLeaves() { return employeeUsedLeaves; }
    public int getEmployeeMaternityLeavesUsed() { return maternityLeavesUsed; }
    public int getEmployeeParentalLeavesUsed() { return parentalLeavesUsed; }
    public void incrementMaternityLeaves() { this.maternityLeavesUsed++; }
    public void incrementParentalLeaves() { this.parentalLeavesUsed++; }

    public void updateEmployeeLeaveBalance(LeaveType LeaveType, int numberOfLeaveDays) {
        employeeLeaveBalance.put(LeaveType, employeeLeaveBalance.getOrDefault(LeaveType, 0) - numberOfLeaveDays);
    }

    public void updateEmployeeUsedLeaves(LeaveType LeaveType, int numberOfLeaveDays) {
        employeeUsedLeaves.put(LeaveType, employeeUsedLeaves.getOrDefault(LeaveType, 0) + numberOfLeaveDays);
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Type: %s, Manager: %s",
                employeeID, employeeName, employeeType, managerID);
    }
}

