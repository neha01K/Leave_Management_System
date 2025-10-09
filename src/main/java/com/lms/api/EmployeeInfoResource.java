package com.lms.api;

import com.lms.queries.QueriesConstantForRegister;
import com.lms.util.DBConnection;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.json.JSONObject;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


@Path("/employees")
public class EmployeeInfoResource {

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeInfo(@Context HttpServletRequest httpRequest) {

        HttpSession session = httpRequest.getSession(false);

        if(session!=null || session.getAttribute("employeeID")!=null){
            Map<String, Object> employeesData = new HashMap<>();
            employeesData.put("employeeID", session.getAttribute("employeeID"));
            employeesData.put("employeeName", session.getAttribute("employeeName"));

            return Response.ok(employeesData).build();
        }
        else{
            Map<String, String> error = new HashMap<>();

            error.put("error","Not Authenticated");
            return Response.status(Response.Status.UNAUTHORIZED).entity(error).build();
        }
    }
}
