package com.lms.services;

import com.lms.exceptions.EmployeeNotFound;
import com.lms.models.Employee;
import com.lms.models.LeaveRequest;
import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveStatus;
import com.lms.models.enums.LeaveType;

import java.util.*;

public class LeaveService {

    Map<String, LeaveRequest> leaveRequest;
    EmployeeService employeeService;

    public LeaveService(EmployeeService employeeService) {
        leaveRequest = new HashMap<>();
        this.employeeService = employeeService;
    }

    public void submitLeaveRequest(LeaveRequest request) {
        leaveRequest.put(request.getLeaveRequestID(), request);
    }

    public List<LeaveRequest> getPendingRequestsForApprover(String approverID) throws EmployeeNotFound {

        Employee approver = employeeService.getEmployee(approverID);
        List<LeaveRequest> pendingRequests = new ArrayList<>();

        for (LeaveRequest leaveRequest : leaveRequest.values()) {

            if (leaveRequest.getLeaveStatus() == LeaveStatus.PENDING) {
                Employee employee = employeeService.getEmployee(leaveRequest.getEmployeeID());

                if (employee != null) {

                    if (approver.getEmployeeType() == EmployeeType.MANAGER) {
                        pendingRequests.add(leaveRequest);
                    }
                    else if (approver.getEmployeeType() == EmployeeType.LEAD) {
                        if (employee.getEmployeeType() == EmployeeType.EXECUTIVE &&
                                approverID.equals(employee.getManagerID())) {
                            pendingRequests.add(leaveRequest);
                        }
                    }
                }
            }
        }
        return pendingRequests;
    }

    public void approveLeave(LeaveRequest leaveRequest, String approverID) throws EmployeeNotFound{
        Employee employee = employeeService.getEmployee(leaveRequest.getEmployeeID());

        //deducts from the available balance
        employee.updateEmployeeLeaveBalance(leaveRequest.getLeaveType(), -leaveRequest.getNumberOfDaysOfLeave());

        //adds to the used leave total
        employee.updateEmployeeUsedLeaves(leaveRequest.getLeaveType(), leaveRequest.getNumberOfDaysOfLeave());

        if (leaveRequest.getLeaveType() == LeaveType.MATERNITY_LEAVE) {
            employee.incrementMaternityLeaves();
        }
        if (leaveRequest.getLeaveType() == LeaveType.PARENTAL_LEAVE) {
            employee.incrementParentalLeaves();
        }

        leaveRequest.setLeaveStatus(LeaveStatus.APPROVED);
        leaveRequest.setLeaveApprovedBy(approverID);
    }

    public void rejectLeave(LeaveRequest leaveRequest, String approverID) {
        leaveRequest.setLeaveStatus(LeaveStatus.REJECTED);
        leaveRequest.setLeaveApprovedBy(approverID);
    }

    public List<LeaveRequest> getLeaveHistoryForEmployee(String employeeID) {
        List<LeaveRequest> employeeLeaveHistory = new ArrayList<>();
        for (LeaveRequest leaveRequest : leaveRequest.values()) {
            if (leaveRequest.getEmployeeID().equals(employeeID)) {
                employeeLeaveHistory.add(leaveRequest);
            }
        }
        return employeeLeaveHistory;
    }
}