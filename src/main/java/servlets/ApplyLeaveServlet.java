package servlets;

import com.lms.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.lms.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ApplyLeave")
public class ApplyLeaveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeID = SessionUtils.checkLoggedInEmployee(request, response);
        if(employeeID == null)
            return;

        String leaveType = request.getParameter("leaveType");
        String leaveStartDate = request.getParameter("leaveStartDate");
        String leaveEndDate = request.getParameter("leaveEndDate");
        String leaveReason = request.getParameter("leaveReason");

        //response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO leave_history (employeeID, leaveType, leaveStartDate, leaveEndDate, leaveReason) VALUES (?, ?, ?, ?, ?)"
            );
            preparedStatement.setString(1, employeeID);
            preparedStatement.setString(2, leaveType);
            preparedStatement.setString(3, leaveStartDate);
            preparedStatement.setString(4, leaveEndDate);
            preparedStatement.setString(5, leaveReason);
            preparedStatement.executeUpdate();

            String column = "";
            switch (leaveType) {
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
                preparedStatementUpdate.setString(1, leaveEndDate);
                preparedStatementUpdate.setString(2, leaveStartDate);
                preparedStatementUpdate.setString(3, employeeID);
                preparedStatementUpdate.executeUpdate();
            }

            out.print("SUCCESS");

        } catch (Exception exception) {
            exception.printStackTrace();
            response.setContentType("text/plain");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("ERROR: " + exception.getMessage());
        }
    }
}
