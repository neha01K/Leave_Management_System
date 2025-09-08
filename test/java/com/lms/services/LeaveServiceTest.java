package com.lms.services;

import com.lms.exceptions.EmployeeNotFound;
import com.lms.exceptions.InvalidDateRange;
import com.lms.models.LeaveRequest;
import com.lms.models.enums.LeaveStatus;
import com.lms.models.enums.LeaveType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LeaveServiceTest {

    @Test
    public void shouldSetRejectedStatus_WhenRejectLeaveCalled() throws InvalidDateRange {
        LeaveService leaveService = new LeaveService(new EmployeeService());
        LeaveRequest leaveRequest = new LeaveRequest("EMP123", LeaveType.CASUAL_LEAVE,
                LocalDate.of(2025, 9, 10), LocalDate.of(2025,9,12),
                "Family Function");

        String approverID = "EMP101";

        leaveService.rejectLeave(leaveRequest,approverID);

        assertEquals(LeaveStatus.REJECTED, leaveRequest.getLeaveStatus());
    }

    @Test
    public void shouldSetApprovedStatus_WhenApproved() throws EmployeeNotFound, InvalidDateRange {
        LeaveService leaveService = new LeaveService(new EmployeeService());
        LeaveRequest leaveRequest = new LeaveRequest("EMP123", LeaveType.CASUAL_LEAVE,
                LocalDate.of(2025, 9, 10), LocalDate.of(2025,9,12),
                "Family Function");

        String approverID = "EMP101";

        leaveService.approveLeave(leaveRequest, approverID);

        assertEquals(LeaveStatus.APPROVED, leaveRequest.getLeaveStatus());
    }
}
