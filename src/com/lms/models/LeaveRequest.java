package com.lms.models;

import com.lms.exceptions.InvalidDateRange;
import com.lms.models.enums.LeaveType;
import com.lms.models.enums.LeaveStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaveRequest {
    private String leaveRequestID;
    private String employeeID;
    private LeaveType leaveType;
    private LocalDate leaveStartDate;
    private LocalDate leaveEndDate;
    private int numberOfDaysOfLeave;
    private String leaveReason;
    private LeaveStatus leaveStatus;
    private String leaveApprovedBy;
    private LocalDate leaveRequestDate;
    private String medicalCertificate;
    private String parenthoodCertificate;

    public LeaveRequest(String employeeID, LeaveType leaveType, LocalDate leaveStartDate,
                        LocalDate leaveEndDate, String leaveReason) throws InvalidDateRange {
        System.out.println("Hello Git");
        this.leaveRequestID = generateLeaveRequestID();
        this.employeeID = employeeID;
        this.leaveType = leaveType;
        this.leaveStartDate = leaveStartDate;
        this.leaveEndDate = leaveEndDate;
        this.numberOfDaysOfLeave = (int) ChronoUnit.DAYS.between(leaveStartDate, leaveEndDate) + 1;
        this.leaveReason = leaveReason;
        this.leaveStatus = LeaveStatus.PENDING;
        this.leaveRequestDate = LocalDate.now();
    }

    private String generateLeaveRequestID() {
        return "REQ" + System.currentTimeMillis() % 10000;
    }

    // Getters and Setters
    public String getLeaveRequestID() {
        return leaveRequestID;
    }
    public String getEmployeeID() {
        return employeeID;
    }
    public LeaveType getLeaveType() {
        return leaveType;
    }
    public LocalDate getLeaveStartDate() {
        return leaveStartDate;
    }
    public LocalDate getLeaveEndDate() {
        return leaveEndDate;
    }
    public int getNumberOfDaysOfLeave() {
        return numberOfDaysOfLeave;
    }
    public String getLeaveReason() {
        return leaveReason;
    }
    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }
    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }
    public String getLeaveApprovedBy() {
        return leaveApprovedBy;
    }
    public void setLeaveApprovedBy(String leaveApprovedBy) {
        this.leaveApprovedBy = leaveApprovedBy;
    }
    public LocalDate getLeaveRequestDate() {
        return leaveRequestDate;
    }
    public String getMedicalCertificate() {
        return medicalCertificate;
    }
    public void setMedicalCertificate(String medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }
    public String getParenthoodCertificate() {
        return parenthoodCertificate;
    }
    public void setParenthoodCertificate(String parenthoodCertificate) {
        this.parenthoodCertificate = parenthoodCertificate;
    }

    @Override
    public String toString() {
        return String.format("Request ID: %s, Employee: %s, Type: %s, Period: %s to %s (%d days), Status: %s",
                leaveRequestID, employeeID, leaveType, leaveStartDate, leaveEndDate, numberOfDaysOfLeave, leaveStatus);
    }
}