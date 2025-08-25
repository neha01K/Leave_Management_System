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

    public void submitLeaveRequest(LeaveRequest r) {
        leaveReqs.put(r.getRequestId(), r);
    }

    public List<LeaveRequest> getPendingRequestsForApprover(String apprId) throws EmployeeNotFound {

        Employee approver = empService.getEmployee(apprId);
        List<LeaveRequest> pendingRequests = new ArrayList<>();

        for (LeaveRequest request : leaveReqs.values()) {

            if (request.getStatus() == LeaveStatus.PENDING) {
                Employee emp = empService.getEmployee(request.getEmployeeId());

                if (emp != null) {

                    if (approver.getType() == EmployeeType.MANAGER) {
                        pendingRequests.add(request);
                    }
                    else if (approver.getType() == EmployeeType.LEAD) {
                        if (emp.getType() == EmployeeType.EXECUTIVE &&
                                apprId.equals(emp.getManagerId())) {
                            pendingRequests.add(request);
                        }
                    }
                }
            }
        }
        return pendingRequests;
    }

    public void approveLeave(LeaveRequest request, String approverId) throws EmployeeNotFound{
        Employee e = empService.getEmployee(request.getEmployeeId());

        e.updateLeaveBalance(request.getLeaveType(), -request.getNumberOfDays());
        e.updateUsedLeaves(request.getLeaveType(), request.getNumberOfDays());
        if (request.getLeaveType() == LeaveType.MATERNITY_LEAVE) {
            e.incrementMaternityLeaves();
        }
        if (request.getLeaveType() == LeaveType.PARENTAL_LEAVE) {
            e.incrementParentalLeaves();
        }
        request.setStatus(LeaveStatus.APPROVED);
        request.setApprovedBy(approverId);
    }

    public void rejectLeave(LeaveRequest r, String apprId) {
        r.setStatus(LeaveStatus.REJECTED);
        r.setApprovedBy(apprId);
    }

    public List<LeaveRequest> getLeaveHistoryForEmployee(String empId) {
        List<LeaveRequest> history = new ArrayList<>();
        for (LeaveRequest req : leaveReqs.values()) {
            if (req.getEmployeeId().equals(empId)) {
                history.add(req);
            }
        }
        return history;
    }
}