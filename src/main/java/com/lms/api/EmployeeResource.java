package com.lms.api;

import com.lms.queries.QueriesConstantForRegister;
import com.lms.util.DBConnection;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.json.JSONObject;
import java.sql.*;


@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(
            @FormParam("registerEmployeeName") String employeeName,
            @FormParam("registerEmployeeDesignation") String employeeDesignation,
            @FormParam("registerEmployeeEmail") String employeeEmail) {

        try (Connection connection = DBConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(QueriesConstantForRegister.COUNT_EMPLOYEE);
            ResultSet resultSet = preparedStatement.executeQuery();

            int employeeCount = 0;
            if (resultSet.next()) employeeCount = resultSet.getInt(1);

            String employeeID = "EMP" + String.format("%03d", employeeCount + 1);

            PreparedStatement insertPreparedStatement = connection.prepareStatement(QueriesConstantForRegister.REGISTER_EMPLOYEE);
            insertPreparedStatement.setString(1, employeeID);
            insertPreparedStatement.setString(2, employeeName);
            insertPreparedStatement.setString(3, employeeDesignation);
            insertPreparedStatement.setString(4, employeeEmail);
            insertPreparedStatement.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            insertPreparedStatement.executeUpdate();

            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("employeeId", employeeID);
            return Response.status(Response.Status.CREATED).entity(response.toString()).build();

        } catch (Exception exception) {
            exception.printStackTrace();

            JSONObject error = new JSONObject();
            error.put("status", "error");
            error.put("message", exception.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
        }
    }

    @GET
    @Path("/{employeeID}")
    public Response getEmployee(@PathParam("employeeID") String employeeID) {
        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(QueriesConstantForRegister.RETRIEVE_EMPLOYEE_DATA);
            preparedStatement.setString(1, employeeID);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                JSONObject notFoundError = new JSONObject();
                notFoundError.put("status","not_found");
                return Response.status(Response.Status.NOT_FOUND).entity(notFoundError.toString()).build();
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeeId", resultSet.getString("employeeId"));
            jsonObject.put("employeeName", resultSet.getString("employeeName"));
            jsonObject.put("designation", resultSet.getString("designation"));
            jsonObject.put("email", resultSet.getString("email"));
            return Response.ok(jsonObject.toString()).build();
        }
        catch (Exception exception) {
            exception.printStackTrace();
            JSONObject errorObject = new JSONObject();
            errorObject.put("status","error");
            errorObject.put("message", exception.getMessage());
            return Response.serverError().entity(errorObject.toString()).build();
        }
    }
}
