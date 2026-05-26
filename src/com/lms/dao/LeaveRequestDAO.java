package com.lms.dao;

import com.lms.models.LeaveRequest;
import com.lms.models.enums.LeaveStatus;
import com.lms.models.enums.LeaveType;
import com.lms.utils.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaveRequestDAO implements LeaveRequestDAOInterface {

    public void saveLeaveRequest(LeaveRequest leaveRequest) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.INSERT_LEAVE_REQUEST)) {

            preparedStatement.setString(1, leaveRequest.getLeaveRequestID());
            preparedStatement.setString(2, leaveRequest.getEmployeeID());
            preparedStatement.setString(3, leaveRequest.getLeaveType().name());
            preparedStatement.setDate(4, Date.valueOf(leaveRequest.getLeaveStartDate()));
            preparedStatement.setDate(5, Date.valueOf(leaveRequest.getLeaveEndDate()));
            preparedStatement.setInt(6, leaveRequest.getNumberOfDaysOfLeave());
            preparedStatement.setString(7, leaveRequest.getLeaveReason());
            preparedStatement.setString(8, leaveRequest.getLeaveStatus().name());
            preparedStatement.setString(9, leaveRequest.getLeaveApprovedBy());
            preparedStatement.setDate(10, Date.valueOf(leaveRequest.getLeaveRequestDate()));
            preparedStatement.setString(11, leaveRequest.getMedicalCertificate());
            preparedStatement.setString(12, leaveRequest.getParenthoodCertificate());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public List<LeaveRequest> getLeaveHistoryForEmployee(String employeeID) {
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_LEAVE_REQUESTS_BY_EMPLOYEE)) {

            preparedStatement.setString(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                leaveRequests.add(mapLeaveRequest(resultSet));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return leaveRequests;
    }

    public List<LeaveRequest> getPendingLeaveRequests() {
        List<LeaveRequest> leaveRequests = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.SELECT_PENDING_LEAVE_REQUESTS);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                leaveRequests.add(mapLeaveRequest(resultSet));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return leaveRequests;
    }

    public void updateLeaveRequestStatus(LeaveRequest leaveRequest) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLQueries.UPDATE_LEAVE_REQUEST_STATUS)) {

            preparedStatement.setString(1, leaveRequest.getLeaveStatus().name());
            preparedStatement.setString(2, leaveRequest.getLeaveApprovedBy());
            preparedStatement.setString(3, leaveRequest.getLeaveRequestID());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private LeaveRequest mapLeaveRequest(ResultSet resultSet) throws SQLException {
        LeaveRequest leaveRequest = new LeaveRequest(
                resultSet.getString("leave_request_id"),
                resultSet.getString("employee_id"),
                LeaveType.valueOf(resultSet.getString("leave_type")),
                resultSet.getDate("start_date").toLocalDate(),
                resultSet.getDate("end_date").toLocalDate(),
                resultSet.getInt("number_of_days"),
                resultSet.getString("reason"),
                LeaveStatus.valueOf(resultSet.getString("status")),
                resultSet.getString("approved_by"),
                resultSet.getDate("request_date").toLocalDate()
        );
        leaveRequest.setMedicalCertificate(resultSet.getString("medical_certificate"));
        leaveRequest.setParenthoodCertificate(resultSet.getString("parenthood_certificate"));
        return leaveRequest;
    }
}
