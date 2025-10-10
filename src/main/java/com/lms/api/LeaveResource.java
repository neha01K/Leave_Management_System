package com.lms.api;

import com.lms.util.DBConnection;
import com.lms.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import servlets.LeaveRequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.lms.queries.*;

@Path("/leave")
public class LeaveResource {

    @POST
    @Path("/apply")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response applyLeave(LeaveRequest leaveRequest, @Context HttpServletRequest httpRequest) throws ServletException, IOException {

        String employeeID = SessionUtils.checkLoggedInEmployee(httpRequest, null);
        if (employeeID == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("ERROR: Not logged in").build();
        }

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(QueriesConstant.ADDING_LEAVE_INTO_HISTORY);
            preparedStatement.setString(1, employeeID);
            preparedStatement.setString(2, leaveRequest.leaveType);
            preparedStatement.setString(3, leaveRequest.leaveStartDate);
            preparedStatement.setString(4, leaveRequest.leaveEndDate);
            preparedStatement.setString(5, leaveRequest.leaveReason);
            preparedStatement.executeUpdate();

            String column = "";
            switch (leaveRequest.leaveType) {
                case "Sick Leave":
                    column = "sickLeave";
                    break;
                case "Casual Leave":
                    column = "casualLeave";
                    break;
                case "Annual Leave":
                    column = "annualLeave";
                    break;
                default:
                    column = "";
                    break;
            }

            if (!column.isEmpty()) {
                PreparedStatement preparedStatementUpdate = connection.prepareStatement(
                        "UPDATE leaveBalance SET " + column + " = " + column + " - DATEDIFF(?, ?) + 1 WHERE employeeId=?"
                );
                preparedStatementUpdate.setString(1, leaveRequest.leaveEndDate);
                preparedStatementUpdate.setString(2, leaveRequest.leaveStartDate);
                preparedStatementUpdate.setString(3, employeeID);
                preparedStatementUpdate.executeUpdate();
            }

            return Response.ok("SUCCESS").build();

        } catch (Exception exception) {
            exception.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("ERROR: " + exception.getMessage()).build();
        }
    }
}
