
package com.lms.services;

import com.lms.exceptions.InvalidDateRange;
import com.lms.exceptions.InvalidLeaveRequest;
import com.lms.models.Employee;
import com.lms.models.enums.LeaveType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidationService {

    public boolean validateLeaveTypeForEmployee(Employee employee, LeaveType leaveType) {
        if (leaveType == LeaveType.MATERNITY_LEAVE) {
            if (employee.getEmployeeMaternityLeavesUsed() >= 2) {
                System.out.println("Maximum maternity leaves (2) already used!");
                return false;
            }
        }

        if (leaveType == LeaveType.PARENTAL_LEAVE) {
            if (employee.getEmployeeParentalLeavesUsed() >= 2) {
                System.out.println("Maximum parental leaves (2) already used!");
                return false;
            }
        }

        if(leaveType==null) return false;

        return true;
    }

    public boolean validateLeaveRequest(Employee employee, LeaveType leaveType,
                                        LocalDate leaveStartDate, LocalDate leaveEndDate) throws InvalidLeaveRequest, InvalidDateRange {


        int leavedDays = (int) ChronoUnit.DAYS.between(leaveStartDate, leaveEndDate) + 1;
        int leaveBalance = employee.getEmployeeLeaveBalance().getOrDefault(leaveType, 0);

        if(leaveStartDate==null || leaveEndDate==null){
            throw new NullPointerException("Leave Start or End Date can't be Null");
        }

        if(leaveStartDate.isAfter(leaveEndDate)){
            throw new InvalidDateRange(leaveStartDate, leaveEndDate);
        }
        if(leaveStartDate.isBefore(LocalDate.now())){
            throw new InvalidDateRange("You are making request for past dates.");
        }
        if (leaveType == LeaveType.CASUAL_LEAVE) {
            if (leavedDays > 2) {
                throw new InvalidLeaveRequest("Cannot take more than 2 casual leaves at a time!");
            }
            if (leavedDays > leaveBalance){
               throw new InvalidLeaveRequest("Insufficient casual leave balance!");
            }
        }

        if (leaveType == LeaveType.EARNED_LEAVE && (leaveBalance - leavedDays) < -15) {
            throw new InvalidLeaveRequest("Cannot exceed negative balance limit of 15 days for Earned Leave!");
        }

        if (leaveType == LeaveType.SICK_LEAVE && (leaveBalance - leavedDays) < -12) {
            throw new InvalidLeaveRequest("Cannot exceed negative balance limit of 12 days for Sick Leave!");
        }

        if (leaveType == LeaveType.LEAVE_WITHOUT_PAY && leavedDays > 180) {
            throw new InvalidLeaveRequest("Cannot take more than 180 days of Leave Without Pay!");
        }
        return true;
    }
}