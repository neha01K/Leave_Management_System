package com.lms.models;

import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTest {

    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = new Employee("Kusha", "kusha@gmail.com", EmployeeType.LEAD, LocalDate.now());
    }

    @Test
    public void employeeLeaveBalanceGenerated_WhenNewEmployeeCreated(){
        assertNotNull(employee.getEmployeeLeaveBalance());
        assertTrue(employee.getEmployeeLeaveBalance().containsKey(LeaveType.CASUAL_LEAVE));
    }

    @Test
    public void employeeUsedBalancedGenerated_WhenNewEmployeeCreated(){
        assertNotNull(employee.getEmployeeUsedLeaves());
        assertEquals(0, employee.getEmployeeUsedLeaves().get(LeaveType.CASUAL_LEAVE));
    }

    @Test
    public void  employeeLeaveBalanceDeducted_WhenAppliedForLeave(){
        int leaveBalanceNumberBefore = employee.getEmployeeLeaveBalance().get(LeaveType.CASUAL_LEAVE);

        employee.updateEmployeeLeaveBalance(LeaveType.CASUAL_LEAVE, 3);

        assertEquals(leaveBalanceNumberBefore-3, employee.getEmployeeLeaveBalance().get(LeaveType.CASUAL_LEAVE));
    }

    @Test
    public void  employeeLeavesInLeaveBalance_NotDeducted_WhenNoLeaveApplied(){
        int leaveBalanceNumberBefore = employee.getEmployeeLeaveBalance().get(LeaveType.CASUAL_LEAVE);

        employee.updateEmployeeLeaveBalance(LeaveType.CASUAL_LEAVE, 0);

        assertEquals(leaveBalanceNumberBefore, employee.getEmployeeLeaveBalance().get(LeaveType.CASUAL_LEAVE));
    }

    @Test
    public void employeeUsedLeavesIncreased_WhenAppliedForLeave(){
        int usedLeavesBefore = employee.getEmployeeUsedLeaves().get(LeaveType.CASUAL_LEAVE);

        employee.updateEmployeeUsedLeaves(LeaveType.CASUAL_LEAVE, 3);

        assertEquals(usedLeavesBefore+3,employee.getEmployeeUsedLeaves().get(LeaveType.CASUAL_LEAVE));
    }

    @Test
    public void employeeUsedLeaves_NotIncreased_WhenNoLeavesUsed(){
        int usedLeavesBefore = employee.getEmployeeUsedLeaves().get(LeaveType.CASUAL_LEAVE);

        employee.updateEmployeeUsedLeaves(LeaveType.CASUAL_LEAVE, 0);

        assertEquals(usedLeavesBefore,employee.getEmployeeUsedLeaves().get(LeaveType.CASUAL_LEAVE));
    }


}
