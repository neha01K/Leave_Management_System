package com.lms.api;

import com.lms.util.DBConnection;
import com.lms.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static com.lms.queries.QueriesConstant.FETCHING_LEAVE_BALANCE;

@Path("/leavebalance")
public class LeaveBalanceResource {

    @Context
    private HttpServletRequest httpRequest;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeaveBalance() throws ServletException, IOException {

        String employeeID  = SessionUtils.checkLoggedInEmployee(httpRequest, null);

        if(employeeID==null){
            JSONObject error = new JSONObject();
            error.put("error", "Not authorized");
            return Response.status(Response.Status.UNAUTHORIZED).entity(error.toString()).build();
        }

        JSONObject jsonObject = new JSONObject();

        try(Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(FETCHING_LEAVE_BALANCE)){
            preparedStatement.setString(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                jsonObject.put("sickLeave", resultSet.getInt("sickLeave"));
                jsonObject.put("casualLeave", resultSet.getInt("casualLeave"));
                jsonObject.put("annualLeave", resultSet.getInt("annualLeave"));
            }
        }

        catch(Exception exception){
            JSONObject error = new JSONObject();
            error.put("error", "Server error");
            error.put("message", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }

        return Response.ok(jsonObject.toString()).build();
    }
}
