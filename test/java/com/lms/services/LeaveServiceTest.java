package com.lms.services;

import com.lms.exceptions.EmployeeNotFound;
import com.lms.exceptions.InvalidDateRange;
import com.lms.models.Employee;
import com.lms.models.LeaveRequest;
import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveStatus;
import com.lms.models.enums.LeaveType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaveServiceTest {

    private Employee employee = new Employee("Kusha", "kusha@gmail.com", EmployeeType.LEAD, LocalDate.now());
    private String employeeID = employee.getEmployeeID();
    private String approverID = "EMP101";


    @Test
    public void shouldSetRejectedStatusOnLeave_WhenRejectLeaveCalled() throws InvalidDateRange {
        LeaveService leaveService = new LeaveService(new EmployeeService());
        LeaveRequest leaveRequest = new LeaveRequest(employeeID, LeaveType.CASUAL_LEAVE,
                LocalDate.of(2025, 9, 10), LocalDate.of(2025,9,12),
                "Family Function");

        leaveService.rejectLeave(leaveRequest,approverID);

        assertEquals(LeaveStatus.REJECTED, leaveRequest.getLeaveStatus());
    }

    @Test
    public void shouldSetApprovedStatusOnLeave_WhenApproved() throws EmployeeNotFound, InvalidDateRange {
        LeaveService leaveService = new LeaveService(new EmployeeService());
        LeaveRequest leaveRequest = new LeaveRequest(employeeID, LeaveType.CASUAL_LEAVE,
                LocalDate.of(2025, 9, 10), LocalDate.of(2025,9,12),
                "Family Function");


        leaveService.approveLeave(leaveRequest, approverID);

        assertEquals(LeaveStatus.APPROVED, leaveRequest.getLeaveStatus());
    }
}