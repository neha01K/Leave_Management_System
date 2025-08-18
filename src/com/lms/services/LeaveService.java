package com.lms.services;

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

    public List<LeaveRequest> getPendingRequestsForApprover(String apprId) {
        Employee appr = empService.getEmployee(apprId);
        List<LeaveRequest> pendingRequests = new ArrayList<>();
        for (LeaveRequest request : leaveReqs.values()) {
            if (request.getStatus() == LeaveStatus.PENDING) {
                Employee emp = empService.getEmployee(request.getEmployeeId());
                if (emp != null) {
                    if (appr.getType() == EmployeeType.MANAGER) {
                        pendingRequests.add(request);
                    } else if (appr.getType() == EmployeeType.LEAD) {
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

    public void approveLeave(LeaveRequest r, String apprId) {
        Employee e = empService.getEmployee(r.getEmployeeId());
        e.updateLeaveBalance(r.getLeaveType(), -r.getNumberOfDays());
        e.updateUsedLeaves(r.getLeaveType(), r.getNumberOfDays());
        if (r.getLeaveType() == LeaveType.MATERNITY_LEAVE) {
            e.incrementMaternityLeaves();
        }
        if (r.getLeaveType() == LeaveType.PARENTAL_LEAVE) {
            e.incrementParentalLeaves();
        }
        r.setStatus(LeaveStatus.APPROVED);
        r.setApprovedBy(apprId);
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