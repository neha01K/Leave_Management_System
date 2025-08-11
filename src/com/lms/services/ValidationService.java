
package com.lms.services;

import com.lms.models.Employee;
import com.lms.models.enums.LeaveType;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ValidationService {

    public boolean validateLeaveTypeForEmployee(Employee employee, LeaveType leaveType) {
        if (leaveType == LeaveType.MATERNITY_LEAVE) {
            if (employee.getMaternityLeavesUsed() >= 2) {
                System.out.println("Maximum maternity leaves (2) already used!");
                return false;
            }
        }

        if (leaveType == LeaveType.PARENTAL_LEAVE) {
            if (employee.getParentalLeavesUsed() >= 2) {
                System.out.println("Maximum parental leaves (2) already used!");
                return false;
            }
        }

        return true;
    }

    public boolean validateLeaveRequest(Employee employee, LeaveType leaveType,
                                        LocalDate startDate, LocalDate endDate) {
        int days = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
        int balance = employee.getLeaveBalance().getOrDefault(leaveType, 0);

        if (leaveType == LeaveType.CASUAL_LEAVE) {
            if (days > 2) {
                System.out.println("Cannot take more than 2 casual leaves at a time!");
                return false;
            }
            if (days > balance) {
                System.out.println("Insufficient casual leave balance!");
                return false;
            }
        }

        if (leaveType == LeaveType.EARNED_LEAVE && (balance - days) < -15) {
            System.out.println("Cannot exceed negative balance limit of 15 days for Earned Leave!");
            return false;
        }

        if (leaveType == LeaveType.SICK_LEAVE && (balance - days) < -12) {
            System.out.println("Cannot exceed negative balance limit of 12 days for Sick Leave!");
            return false;
        }

        if (leaveType == LeaveType.LEAVE_WITHOUT_PAY && days > 180) {
            System.out.println("Cannot take more than 180 days of Leave Without Pay!");
            return false;
        }

        return true;
    }
}