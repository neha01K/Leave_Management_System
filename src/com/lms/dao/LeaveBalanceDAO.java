package com.lms.dao;

import com.lms.models.Employee;
import com.lms.models.enums.LeaveType;
import com.lms.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
import java.util.Map;

public class LeaveBalanceDAO {

    public void saveInitialBalances(Employee employee) {
        for (Map.Entry<LeaveType, Integer> entry : employee.getEmployeeLeaveBalance().entrySet()) {
            upsertBalance(employee.getEmployeeID(), entry.getKey(), entry.getValue(), 0);
        }
    }

    public Map<LeaveType, Integer> getLeaveBalances(String employeeID) {
        Map<LeaveType, Integer> balances = new EnumMap<>(LeaveType.class);

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_LEAVE_BALANCES_BY_EMPLOYEE)) {

            preparedStatement.setString(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                balances.put(
                        LeaveType.valueOf(resultSet.getString("leave_type")),
                        resultSet.getInt("available_days")
                );
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return balances;
    }

    public void deductLeaveBalance(String employeeID, LeaveType leaveType, int days) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.DEDUCT_LEAVE_BALANCE)) {

            preparedStatement.setInt(1, days);
            preparedStatement.setInt(2, days);
            preparedStatement.setString(3, employeeID);
            preparedStatement.setString(4, leaveType.name());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void upsertBalance(String employeeID, LeaveType leaveType, int availableDays, int usedDays) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPSERT_LEAVE_BALANCE)) {

            preparedStatement.setString(1, employeeID);
            preparedStatement.setString(2, leaveType.name());
            preparedStatement.setInt(3, availableDays);
            preparedStatement.setInt(4, usedDays);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
