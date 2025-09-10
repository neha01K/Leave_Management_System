package com.lms.utils;

import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeePropertiesUtil {
    private static final String FILE_PATH = "src/main/resources/employee.properties";


    public static void saveEmployee(Employee employee) {
        Properties properties = new Properties();


        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException exception) {
            System.out.println("No existing file, will create new.");
        }


        int index = 1;
        while (properties.containsKey("EMPLOYEE." + index + ".ID")) {
            index++;
        }

        String prefix = "EMPLOYEE." + index;
        properties.setProperty(prefix + ".ID", employee.getEmployeeID());
        properties.setProperty(prefix + ".NAME", employee.getEmployeeName());
        properties.setProperty(prefix + ".EMAIL", employee.getEmployeeEmail());
        properties.setProperty(prefix + ".TYPE", employee.getEmployeeType().name());
        properties.setProperty(prefix + ".joiningdate", employee.getEmployeeJoiningDate().toString());

        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH)) {
            properties.store(fileOutputStream, "Employee Data");
            System.out.println("Employee saved to file!");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static List<Employee> getAllEmployeesDetails() {
        List<Employee> employees = new ArrayList<>();
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(FILE_PATH)) {
            properties.load(fileInputStream);
        } catch (IOException exception) {
            System.out.println("No employee file found.");
            return employees;
        }

        Set<String> prefixes = new HashSet<>();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith("EMPLOYEE.") && key.endsWith(".ID")) {
                prefixes.add(key.substring(0, key.lastIndexOf(".")));
            }
        }

        for (String prefix : prefixes) {
            String employeeID = properties.getProperty(prefix + ".ID");
            String employeeName = properties.getProperty(prefix + ".NAME");
            String employeeEmail = properties.getProperty(prefix + ".EMAIL");
            EmployeeType employeeType = EmployeeType.valueOf(properties.getProperty(prefix + ".TYPE"));
            LocalDate employeeJoiningDate = LocalDate.parse(properties.getProperty(prefix + ".joiningdate"));

            Employee employee = new Employee(employeeID, employeeName, employeeEmail, employeeType, employeeJoiningDate);
            employees.add(employee);
        }

        return employees;
    }
}
