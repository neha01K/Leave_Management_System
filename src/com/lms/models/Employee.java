package com.lms.models;

import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Employee {
    private String employeeId;
    private String name;
    private String email;
    private EmployeeType type;
    private String managerId;
    private LocalDate joiningDate;
    private Map<LeaveType, Integer> leaveBalance;
    private Map<LeaveType, Integer> usedLeaves;
    private int maternityLeavesUsed = 0;
    private int parentalLeavesUsed = 0;

    public Employee(String name, String email, EmployeeType type, LocalDate joiningDate) {
        this.employeeId = generateEmployeeId();
        this.name = name;
        this.email = email;
        this.type = type;
        this.joiningDate = joiningDate;
        this.leaveBalance = new HashMap<>();
        this.usedLeaves = new HashMap<>();
        initializeLeaveBalance();
    }

    public Employee(String employeeId, String name, String email, EmployeeType type, LocalDate joiningDate) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.type = type;
        this.joiningDate = joiningDate;
        this.leaveBalance = new HashMap<>();
        this.usedLeaves = new HashMap<>();
        initializeLeaveBalance();
    }


    private String generateEmployeeId() {
        return "EMP" + System.currentTimeMillis() % 10000;
    }

    private void initializeLeaveBalance() {
        LocalDate currentDate = LocalDate.now();
        int monthsWorked = (int) ChronoUnit.MONTHS.between(
                LocalDate.of(currentDate.getYear(), 1, 1),
                joiningDate.isAfter(LocalDate.of(currentDate.getYear(), 1, 1)) ?
                        joiningDate : LocalDate.of(currentDate.getYear(), 1, 1)
        );

        for (LeaveType type : LeaveType.values()) {
            int allocation = type.getYearlyAllocation();

            if ((type == LeaveType.CASUAL_LEAVE || type == LeaveType.SICK_LEAVE) &&
                    joiningDate.getYear() == currentDate.getYear() && monthsWorked > 0) {
                allocation = (allocation * (12 - monthsWorked)) / 12;
            }

            leaveBalance.put(type, allocation);
            usedLeaves.put(type, 0);
        }

        if (joiningDate.getYear() == currentDate.getYear()) {
            int monthsCompleted = (int) ChronoUnit.MONTHS.between(joiningDate, currentDate);
            leaveBalance.put(LeaveType.EARNED_LEAVE, (int) (monthsCompleted * 1.25));
        }
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public EmployeeType getType() { return type; }
    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public Map<LeaveType, Integer> getLeaveBalance() { return leaveBalance; }
    public Map<LeaveType, Integer> getUsedLeaves() { return usedLeaves; }
    public int getMaternityLeavesUsed() { return maternityLeavesUsed; }
    public int getParentalLeavesUsed() { return parentalLeavesUsed; }
    public void incrementMaternityLeaves() { this.maternityLeavesUsed++; }
    public void incrementParentalLeaves() { this.parentalLeavesUsed++; }

    public void updateLeaveBalance(LeaveType type, int days) {
        leaveBalance.put(type, leaveBalance.getOrDefault(type, 0) + days);
    }

    public void updateUsedLeaves(LeaveType type, int days) {
        usedLeaves.put(type, usedLeaves.getOrDefault(type, 0) + days);
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Type: %s, Manager: %s",
                employeeId, name, type, managerId);
    }
}