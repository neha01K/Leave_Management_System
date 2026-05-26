package com.lms.services;

import com.lms.dao.EmployeeDAOInterface;
import com.lms.dao.LeaveRequestDAOInterface;
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
    EmployeeDAOInterface employeeDAO;
    LeaveRequestDAOInterface leaveRequestDAO;

    public LeaveService(EmployeeService employeeService) {
        leaveRequest = new HashMap<>();
        this.employeeService = employeeService;
    }

    public LeaveService(EmployeeDAOInterface employeeDAO, LeaveRequestDAOInterface leaveRequestDAO) {
        leaveRequest = new HashMap<>();
        this.employeeDAO = employeeDAO;
        this.leaveRequestDAO = leaveRequestDAO;
    }

    public void submitLeaveRequest(LeaveRequest request) {
        leaveRequest.put(request.getLeaveRequestID(), request);
        if (leaveRequestDAO != null) {
            leaveRequestDAO.saveLeaveRequest(request);
        }
    }

    public List<LeaveRequest> getPendingRequestsForApprover(String approverID) throws EmployeeNotFound {

        Employee approver = getEmployee(approverID);
        List<LeaveRequest> pendingRequests = new ArrayList<>();

        Collection<LeaveRequest> requests = leaveRequestDAO == null
                ? leaveRequest.values()
                : leaveRequestDAO.getPendingLeaveRequests();

        for (LeaveRequest leaveRequest : requests) {

            if (leaveRequest.getLeaveStatus() == LeaveStatus.PENDING) {
                Employee employee = getEmployee(leaveRequest.getEmployeeID());

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
        Employee employee = getEmployee(leaveRequest.getEmployeeID());

        //deducts from the available balance
        employee.updateEmployeeLeaveBalance(leaveRequest.getLeaveType(), leaveRequest.getNumberOfDaysOfLeave());

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
        if (leaveRequestDAO != null) {
            leaveRequestDAO.updateLeaveRequestStatus(leaveRequest);
        }
    }

    public void rejectLeave(LeaveRequest leaveRequest, String approverID) {
        leaveRequest.setLeaveStatus(LeaveStatus.REJECTED);
        leaveRequest.setLeaveApprovedBy(approverID);
        if (leaveRequestDAO != null) {
            leaveRequestDAO.updateLeaveRequestStatus(leaveRequest);
        }
    }

    public List<LeaveRequest> getLeaveHistoryForEmployee(String employeeID) {
        if (leaveRequestDAO != null) {
            return leaveRequestDAO.getLeaveHistoryForEmployee(employeeID);
        }

        List<LeaveRequest> employeeLeaveHistory = new ArrayList<>();
        for (LeaveRequest leaveRequest : leaveRequest.values()) {
            if (leaveRequest.getEmployeeID().equals(employeeID)) {
                employeeLeaveHistory.add(leaveRequest);
            }
        }
        return employeeLeaveHistory;
    }

    public Map<LeaveType, Integer> getCurrentLeaveBalance(String employeeID) throws EmployeeNotFound {
        Employee employee = getEmployee(employeeID);
        Map<LeaveType, Integer> balance = new HashMap<>(employee.getEmployeeLeaveBalance());

        for (LeaveRequest request : getLeaveHistoryForEmployee(employeeID)) {
            if (request.getLeaveStatus() == LeaveStatus.APPROVED) {
                balance.put(
                        request.getLeaveType(),
                        balance.getOrDefault(request.getLeaveType(), 0) - request.getNumberOfDaysOfLeave()
                );
            }
        }
        return balance;
    }

    private Employee getEmployee(String employeeID) throws EmployeeNotFound {
        if (employeeDAO != null) {
            Employee employee = employeeDAO.getEmployeeDetailByEmployeeID(employeeID);
            if (employee == null) {
                throw new EmployeeNotFound(employeeID);
            }
            return employee;
        }
        return employeeService.getEmployee(employeeID);
    }
}
