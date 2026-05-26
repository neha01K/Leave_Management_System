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
            preparedStatement.setString(6, employee.getManagerID());
            preparedStatement.setString(7, employee.getPasswordHash());

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
                employeesList.add(mapEmployee(resultSet));
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
                return mapEmployee(resultSet);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public Employee getEmployeeDetailByEmail(String email) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_EMPLOYEES_BY_EMAIL)) {

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapEmployee(resultSet);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private Employee mapEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getString("employee_id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                EmployeeType.valueOf(resultSet.getString("type")),
                resultSet.getDate("joining_date").toLocalDate(),
                resultSet.getString("manager_id"),
                resultSet.getString("password_hash")
        );
    }
}
