package com.lms.parameterized_test_cases;

import com.lms.models.*;
import com.lms.models.enums.EmployeeType;
import com.lms.models.enums.LeaveStatus;
import com.lms.models.enums.LeaveType;
import com.lms.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Leave Management System - Complete Test Suite")
public class TestCases {
    private static EmployeeService employeeService;
    private static LeaveService leaveService;
    private static ValidationService validationService;

    @BeforeAll
    @DisplayName("Setup Test Environment")
    static void setupTestEnvironment() {

        employeeService = new EmployeeService();
        leaveService = new LeaveService(employeeService);
        validationService = new ValidationService();

        assertNotNull(employeeService, "EmployeeService should be initialized");
        assertNotNull(leaveService, "LeaveService should be initialized");
        assertNotNull(validationService, "ValidationService should be initialized");

        System.out.println("Services initialized successfully");
    }

    @Test
    @Tag("employee")
    @DisplayName("Employee creation")
    void testEmployeeCreation() {
        String employeeName = "Mahesh";
        String employeeEmail = "mahesh@gmail.com";
        EmployeeType employeetype = EmployeeType.EXECUTIVE;
        LocalDate joiningDate = LocalDate.of(2024, 6, 15);

        Employee employee = employeeService.createEmployee(employeeName, employeeEmail, employeetype, joiningDate);

        assertAll("Employee Creation Assertions",
                () -> assertNotNull(employee),
                () -> assertEquals(employeeName, employee.getEmployeeName()),
                () -> assertEquals(employeeEmail, employee.getEmployeeEmail()),
                () -> assertEquals(employeetype, employee.getEmployeeType()),
                () -> assertEquals(joiningDate, employee.getEmployeeJoiningDate()),
                () -> assertNotNull(employee.getEmployeeID()),
                () -> assertTrue(employee.getEmployeeID().startsWith("EMP"))
        );
    }

    static Stream<Arguments> validLeaveTypesProvider() {
        return Stream.of(
                Arguments.of(LeaveType.CASUAL_LEAVE, 10),
                Arguments.of(LeaveType.EARNED_LEAVE, 0),
                Arguments.of(LeaveType.SICK_LEAVE, 12),
                Arguments.of(LeaveType.PARENTAL_LEAVE, 7),
                Arguments.of(LeaveType.MATERNITY_LEAVE, 120)
        );
    }

    @ParameterizedTest(name = "{index}: Verify {0} allocation is {1}")
    @Tag("parameterized")
    @MethodSource("validLeaveTypesProvider")
    @DisplayName("Parameterized - Leave Type Allocations")
    void testLeaveTypeAllocations(LeaveType leaveType, int expectedAllocation) {
        assertEquals(expectedAllocation, leaveType.getYearlyAllocation(),
                String.format("%s should have %d days allocation",
                        leaveType, expectedAllocation));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "sateyndra@example.com",
            "mahendra@company.in",
            "surendra@gmail.com"
    })
    void testValidEmployeeEmail(String employeeEmail) {
        Employee emp = new Employee("Test", employeeEmail, EmployeeType.EXECUTIVE, LocalDate.now());
        assertTrue(emp.getEmployeeEmail().contains("@"), "Email should contain @ symbol");
    }
}
