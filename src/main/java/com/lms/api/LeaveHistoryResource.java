package com.lms.api;

import com.lms.util.DBConnection;
import com.lms.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lms.queries.*;


@Path("/leavehistory")
public class LeaveHistoryResource {

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeaveHistory() throws ServletException, IOException {

        String employeeID = SessionUtils.checkLoggedInEmployee(httpRequest, null);

        if(employeeID==null){
            JSONObject error = new JSONObject();
            error.put("error","not authorized");
            return Response.status(Response.Status.UNAUTHORIZED).entity(error.toString()).build();
        }

        JSONArray jsonLeavesArray = new JSONArray();

        try(Connection connection = DBConnection.getConnection();
        PreparedStatement prepareStatement = connection.prepareStatement(QueriesConstant.FETCHING_EMPLOYEE_LEAVE_HISTORY)){

            prepareStatement.setString(1, employeeID);
            ResultSet resultSet = prepareStatement.executeQuery();

            while(resultSet.next()){

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("leaveType", resultSet.getString("leaveType"));
                jsonObject.put("leaveStartDate", resultSet.getDate("leaveStartDate"));
                jsonObject.put("leaveEndDate", resultSet.getDate("leaveEndDate"));
                jsonObject.put("leaveReason", resultSet.getString("leaveReason"));
                jsonObject.put("status", resultSet.getString("status"));
                jsonObject.put("appliedDate", resultSet.getTimestamp("appliedDate"));

                jsonLeavesArray.put(jsonObject);
            }

        }
        catch(Exception exception){
            JSONObject error = new JSONObject();
            error.put("error","not authorized");
            error.put("message", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }

        return Response.ok(jsonLeavesArray.toString()).build();
    }
}
