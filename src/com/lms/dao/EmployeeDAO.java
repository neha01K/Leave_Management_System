package com.lms.dao;

import com.lms.models.Employee;
import com.lms.models.enums.EmployeeType;
import com.lms.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO  implements EmployeeDAOInterface{

    public void saveEmployee(Employee employee) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_EMPLOYEE)) {

            preparedStatement.setString(1, employee.getEmployeeID());
            preparedStatement.setString(2, employee.getEmployeeName());
            preparedStatement.setString(3, employee.getEmployeeEmail());
            preparedStatement.setString(4, employee.getEmployeeType().name());
            preparedStatement.setDate(5, Date.valueOf(employee.getEmployeeJoiningDate()));

            preparedStatement.executeUpdate();
        }
        catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<Employee> getAllEmployeesDetails() {

        List<Employee> employeesList = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_ALL_EMPLOYEES);
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
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return employeesList;
    }

    public Employee getEmployeeDetailByEmployeeID(String employeeID) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_EMPLOYEES_BY_ID)) {

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
