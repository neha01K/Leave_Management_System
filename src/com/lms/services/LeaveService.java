package com.lms.services;

import com.lms.exceptions.EmployeeNotFound;
import com.lms.models.Employee;
import com.lms.models.LeaveRequest;
import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveStatus;
import com.lms.models.enums.LeaveType;

import java.util.*;

public class LeaveService {

    Map<String, LeaveRequest> leaveReqs;
    EmployeeService empService;

    public LeaveService(EmployeeService empService) {
        leaveReqs = new HashMap<>();
        this.empService = empService;
    }

    public void submitLeaveRequest(LeaveRequest request) {
        leaveReqs.put(request.getRequestId(), request);
    }

    public List<LeaveRequest> getPendingRequestsForApprover(String apprId) throws EmployeeNotFound {

        Employee approver = empService.getEmployee(apprId);
        List<LeaveRequest> pendingRequests = new ArrayList<>();

        for (LeaveRequest request : leaveReqs.values()) {

            if (request.getStatus() == LeaveStatus.PENDING) {
                Employee employee = empService.getEmployee(request.getEmployeeId());

                if (employee != null) {

                    if (approver.getType() == EmployeeType.MANAGER) {
                        pendingRequests.add(request);
                    }
                    else if (approver.getType() == EmployeeType.LEAD) {
                        if (employee.getType() == EmployeeType.EXECUTIVE &&
                                apprId.equals(employee.getManagerId())) {
                            pendingRequests.add(request);
                        }
                    }
                }
            }
        }
        return pendingRequests;
    }

    public void approveLeave(LeaveRequest request, String approverId) throws EmployeeNotFound{
        Employee employee = empService.getEmployee(request.getEmployeeId());

        //deducts from the available balance
        employee.updateLeaveBalance(request.getLeaveType(), -request.getNumberOfDays());

        //adds to the used leave total
        employee.updateUsedLeaves(request.getLeaveType(), request.getNumberOfDays());

        if (request.getLeaveType() == LeaveType.MATERNITY_LEAVE) {
            employee.incrementMaternityLeaves();
        }
        if (request.getLeaveType() == LeaveType.PARENTAL_LEAVE) {
            employee.incrementParentalLeaves();
        }

        request.setStatus(LeaveStatus.APPROVED);
        request.setApprovedBy(approverId);
    }

    public void rejectLeave(LeaveRequest leaveRequest, String approverId) {
        leaveRequest.setStatus(LeaveStatus.REJECTED);
        leaveRequest.setApprovedBy(approverId);
    }

    public List<LeaveRequest> getLeaveHistoryForEmployee(String employeeId) {
        List<LeaveRequest> history = new ArrayList<>();
        for (LeaveRequest req : leaveReqs.values()) {
            if (req.getEmployeeId().equals(employeeId)) {
                history.add(req);
            }
        }
        return history;
    }
}