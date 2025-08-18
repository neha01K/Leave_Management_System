package com.lms.models;

import com.lms.exceptions.InvalidDateRange;
import com.lms.models.enums.LeaveType;
import com.lms.models.enums.LeaveStatus;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LeaveRequest {
    private String requestId;
    private String employeeId;
    private LeaveType leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfDays;
    private String reason;
    private LeaveStatus status;
    private String approvedBy;
    private LocalDate requestDate;
    private String medicalCertificate;
    private String parenthoodCertificate;

    public LeaveRequest(String employeeId, LeaveType leaveType, LocalDate startDate,
                        LocalDate endDate, String reason) throws InvalidDateRange {
        this.requestId = generateRequestId();
        this.employeeId = employeeId;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        this.reason = reason;
        this.status = LeaveStatus.PENDING;
        this.requestDate = LocalDate.now();
    }

    private String generateRequestId() {
        return "REQ" + System.currentTimeMillis() % 10000;
    }

    // Getters and Setters
    public String getRequestId() {
        return requestId;
    }
    public String getEmployeeId() {
        return employeeId;
    }
    public LeaveType getLeaveType() {
        return leaveType;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public int getNumberOfDays() {
        return numberOfDays;
    }
    public String getReason() {
        return reason;
    }
    public LeaveStatus getStatus() {
        return status;
    }
    public void setStatus(LeaveStatus status) {
        this.status = status;
    }
    public String getApprovedBy() {
        return approvedBy;
    }
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    public LocalDate getRequestDate() {
        return requestDate;
    }
    public String getMedicalCertificate() {
        return medicalCertificate;
    }
    public void setMedicalCertificate(String cert) {
        this.medicalCertificate = cert;
    }
    public String getParenthoodCertificate() {
        return parenthoodCertificate;
    }
    public void setParenthoodCertificate(String cert) {
        this.parenthoodCertificate = cert;
    }

    @Override
    public String toString() {
        return String.format("Request ID: %s, Employee: %s, Type: %s, Period: %s to %s (%d days), Status: %s",
                requestId, employeeId, leaveType, startDate, endDate, numberOfDays, status);
    }
}