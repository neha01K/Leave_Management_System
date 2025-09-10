package com.lms.dao;

import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;
import com.lms.utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    public void saveEmployee(Employee employee) {

        String sqlQuery = "INSERT INTO employees_details(employee_id, name, email, type, joining_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, employee.getEmployeeID());
            preparedStatement.setString(2, employee.getEmployeeName());
            preparedStatement.setString(3, employee.getEmployeeEmail());
            preparedStatement.setString(4, employee.getEmployeeType().name());
            preparedStatement.setDate(5, Date.valueOf(employee.getEmployeeJoiningDate()));

            preparedStatement.executeUpdate();
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<Employee> getAllEmployeesDetails() {

        List<Employee> employeesList = new ArrayList<>();

        String sqlQuery = "SELECT * FROM employees_details";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getString("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        EmployeeType.valueOf(resultSet.getString("type")),
                        resultSet.getDate("joining_date").toLocalDate()
                );
                employeesList.add(employee);
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return employeesList;
    }

    public Employee getEmployeeDetailByID(String employeeID) {

        String sqlQuery = "SELECT * FROM employees_details WHERE employee_id=?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, employeeID);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Employee(
                        resultSet.getString("employee_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        EmployeeType.valueOf(resultSet.getString("type")),
                        resultSet.getDate("joining_date").toLocalDate()
                );
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
