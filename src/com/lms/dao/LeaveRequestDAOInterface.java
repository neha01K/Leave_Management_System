package com.lms.dao;

import com.lms.models.LeaveRequest;

import java.util.List;

public interface LeaveRequestDAOInterface {

    void saveLeaveRequest(LeaveRequest leaveRequest);

    List<LeaveRequest> getLeaveHistoryForEmployee(String employeeID);

    List<LeaveRequest> getPendingLeaveRequests();

    void updateLeaveRequestStatus(LeaveRequest leaveRequest);
}
