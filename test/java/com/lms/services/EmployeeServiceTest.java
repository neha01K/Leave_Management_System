package com.lms.services;

import java.time.LocalDate;

import com.lms.exceptions.EmployeeNotFound;
import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp(){
        employeeService = new EmployeeService();
    }

    @Test
    public void managerCreatedWhenRegisterEmployee_Positive(){

         Employee manager=employeeService.createEmployee("Suresh","suresh@gmail.com", EmployeeType.MANAGER, LocalDate.now());

         assertEquals(EmployeeType.MANAGER,manager.getEmployeeType());
         assertNotNull(manager.getEmployeeID());
         assertNull(manager.getManagerID());
    }

    @Test
    public void leadCreatedWhenRegisterEmployee_Positive(){

        Employee lead=employeeService.createEmployee("Lokesh","lokesh@gmail.com", EmployeeType.LEAD, LocalDate.now());

        assertEquals(EmployeeType.LEAD,lead.getEmployeeType());
        assertNotNull(lead.getEmployeeID());
        assertNull(lead.getManagerID());
    }

    @Test
    public void executiveCreatedWhenRegisterEmployee_Positive(){

        Employee executive=employeeService.createEmployee("Ramesh","Ramesh@gmail.com", EmployeeType.EXECUTIVE, LocalDate.now());

        assertEquals(EmployeeType.EXECUTIVE,executive.getEmployeeType());
        assertNotNull(executive.getEmployeeID());
        assertNull(executive.getManagerID());
    }

    @Test
    public void createEmployeeWithNullName_Negative(){
        Employee employee = employeeService.createEmployee("","xyz@gmail.com",EmployeeType.MANAGER,LocalDate.now());

        assertEquals("",employee.getEmployeeName());
    }

    @Test
    public void createEmployeeWithNullEmail_Negative(){
        Employee employee = employeeService.createEmployee("Mankesh","",EmployeeType.EXECUTIVE,LocalDate.now());

        assertEquals("",employee.getEmployeeEmail());
    }

    @Test
    public void throwNullPointerException_WhenCreatingEmployee_WithNullEmployeeType(){

        assertThrows(NullPointerException.class,() -> employeeService.createEmployee("Rajesh","raj@gmail.com",null,LocalDate.now()));
    }

    @Test
    public void createEmployeeWithNoJoiningDate_Negative(){

        assertThrows(NullPointerException.class, () ->
                employeeService.createEmployee("Jitendra","jitendra@gmail.com",EmployeeType.EXECUTIVE,null));

    }

    @Test
    public void getEmployeeObjectWithEmployeeID_Positive() throws EmployeeNotFound {
        Employee sampleEmployee = employeeService.createEmployee("Neha","neha@gmail.com",EmployeeType.EXECUTIVE,LocalDate.now());

        String sampleEmployeeID = sampleEmployee.getEmployeeID();
        assertSame(sampleEmployee, employeeService.getEmployee(sampleEmployeeID));
    }

    @Test
    public void getNoEmployeeWithUnregisteredEmployeeID_Negative() {
        String employeeID = "EMP111";

            assertThrows(EmployeeNotFound.class, () -> employeeService.getEmployee(employeeID));
    }


}
