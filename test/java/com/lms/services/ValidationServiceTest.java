package com.lms.services;

import com.lms.exceptions.InvalidDateRange;
import com.lms.exceptions.InvalidLeaveRequest;
import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {

    private ValidationService validationService;
    private Employee employee;

    @BeforeEach
    public void setUp(){
        validationService = new ValidationService();
        employee = new Employee("Kanak", "kanak@gmail.com", EmployeeType.MANAGER, LocalDate.now());
    }

    @Test
    public void returnTrueForCasualLeaveType(){

        assertTrue(validationService.validateLeaveTypeForEmployee(employee, LeaveType.CASUAL_LEAVE));
    }

    @Test
    public void returnTrueForEarnedLeaveTypeForLeave(){

        assertTrue(validationService.validateLeaveTypeForEmployee(employee, LeaveType.EARNED_LEAVE));
    }

    @Test
    public void returnTrueForSickLeaveType(){

        assertTrue(validationService.validateLeaveTypeForEmployee(employee, LeaveType.SICK_LEAVE));
    }

    @Test
    public void returnTrueForDutyLeaveType(){

        assertTrue(validationService.validateLeaveTypeForEmployee(employee, LeaveType.DUTY_LEAVE));
    }

    @Test
    public void returnFalseForNullValueForLeaveType(){
        assertFalse(validationService.validateLeaveTypeForEmployee(employee, null));
    }

    @Test
    public void returnTrueWhenMaternityLeaveLessThanTwo(){
        employee.incrementMaternityLeaves();
        assertTrue(validationService.validateLeaveTypeForEmployee(employee, LeaveType.MATERNITY_LEAVE));
    }

    @Test
    public void returnTrueWhenParentalLeaveLessThanTwo(){
        employee.incrementParentalLeaves();
        assertTrue(validationService.validateLeaveTypeForEmployee(employee, LeaveType.PARENTAL_LEAVE));
    }

    @Test
    public void returnFalseWhenMaternityLeaveEqualsToTwo(){
        for(int i=0; i<2; i++)
            employee.incrementMaternityLeaves();
        assertFalse(validationService.validateLeaveTypeForEmployee(employee, LeaveType.MATERNITY_LEAVE));
    }

    @Test
    public void returnFalseWhenParentalLeaveEqualsToTwo(){
        for(int i=0; i<2; i++)
            employee.incrementParentalLeaves();
        assertFalse(validationService.validateLeaveTypeForEmployee(employee, LeaveType.PARENTAL_LEAVE));
    }

    @Test
    public void returnFalseWhenMaternityLeaveGreaterThanTwo(){
        for(int i=0; i<5; i++)
            employee.incrementMaternityLeaves();
        assertFalse(validationService.validateLeaveTypeForEmployee(employee, LeaveType.MATERNITY_LEAVE));
    }

    @Test
    public void returnFalseWhenParentalLeaveGreaterThanTwo(){
        for(int i=0; i<5; i++)
            employee.incrementParentalLeaves();
        assertFalse(validationService.validateLeaveTypeForEmployee(employee, LeaveType.PARENTAL_LEAVE));
    }

    @Test
    public void throwNullPointerException_WhenNullLeaveStartDateEntered(){
        assertThrows(NullPointerException.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.SICK_LEAVE, null, LocalDate.now()));
    }

    @Test
    public void throwNullPointerExceptionException_WhenNullLeaveEndDateEntered(){
        assertThrows(NullPointerException.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.SICK_LEAVE,LocalDate.now(), null));
    }

    @Test
    public void throwInvalidDateRangeException_WhenEndDateIsBeforeStartDate(){
        assertThrows(InvalidDateRange.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.SICK_LEAVE, LocalDate.now(), LocalDate.of(1999,1,1)));
    }

    @Test
    public void throwInvalidDateRangeException_WhenStartDateIsBeforeTodayDate(){
        assertThrows(InvalidDateRange.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.SICK_LEAVE, LocalDate.of(1999,1,1), LocalDate.now()));
    }

    @Test
    public void throwInvalidLeaveRequest_ForCasualLeave_WhenApplyForMoreThanTwoDays(){
        assertThrows(InvalidLeaveRequest.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.CASUAL_LEAVE,LocalDate.now(), LocalDate.now().plusDays(3)));
    }

    @Test
    public void throwInvalidLeaveRequest_ForEarnedLeave_WhenInsufficientLeaveBalance(){
        assertThrows(InvalidLeaveRequest.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.EARNED_LEAVE,LocalDate.now(), LocalDate.now().plusDays(30)));
    }

    @Test
    public void throwInvalidLeaveRequest_ForSickeave_WhenInsufficientLeaveBalance(){
        assertThrows(InvalidLeaveRequest.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.SICK_LEAVE,LocalDate.now(), LocalDate.now().plusDays(25)));
    }

    @Test
    public void throwInvalidLeaveRequest_ForLeaveWithoutPay_WhenInsufficientLeaveBalance(){
        assertThrows(InvalidLeaveRequest.class, () ->
                validationService.validateLeaveRequest(employee, LeaveType.SICK_LEAVE,LocalDate.now(), LocalDate.now().plusDays(181)));
    }

    @Test
    public void returnTrueForAnyOtherLeave() throws InvalidDateRange, InvalidLeaveRequest {
        assertTrue(validationService.validateLeaveRequest(employee, LeaveType.CASUAL_LEAVE, LocalDate.now(), LocalDate.now().plusDays(1)));
    }
}
