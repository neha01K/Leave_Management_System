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
        while (properties.containsKey("employee." + index + ".id")) {
            index++;
        }

        String prefix = "employee." + index;
        properties.setProperty(prefix + ".id", employee.getEmployeeId());
        properties.setProperty(prefix + ".name", employee.getName());
        properties.setProperty(prefix + ".email", employee.getEmail());
        properties.setProperty(prefix + ".type", employee.getType().name());
        properties.setProperty(prefix + ".joiningDate", employee.getJoiningDate().toString());

        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH)) {
            properties.store(fileOutputStream, "Employee Data");
            System.out.println("Employee saved to file!");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static List<Employee> loadEmployees() {
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
            if (key.startsWith("employee.") && key.endsWith(".id")) {
                prefixes.add(key.substring(0, key.lastIndexOf(".")));
            }
        }

        for (String prefix : prefixes) {
            String id = properties.getProperty(prefix + ".id");
            String name = properties.getProperty(prefix + ".name");
            String email = properties.getProperty(prefix + ".email");
            EmployeeType type = EmployeeType.valueOf(properties.getProperty(prefix + ".type"));
            LocalDate joiningDate = LocalDate.parse(properties.getProperty(prefix + ".joiningDate"));

            Employee emp = new Employee(id, name, email, type, joiningDate);
            employees.add(emp);
        }

        return employees;
    }
}
