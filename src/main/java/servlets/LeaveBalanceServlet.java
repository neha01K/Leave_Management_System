package servlets;

import com.lms.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lms.util.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LeaveBalance")
public class LeaveBalanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String employeeID = SessionUtils.checkLoggedInEmployee(request, response);
        if(employeeID==null)
            return;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM leaveBalance WHERE employeeId=?"
            );
            preparedStatement.setString(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("sickLeave", resultSet.getInt("sickLeave"));
                jsonObject.put("casualLeave", resultSet.getInt("casualLeave"));
                jsonObject.put("annualLeave", resultSet.getInt("annualLeave"));
                out.print(jsonObject.toString());
            } else {
                out.print("{}");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            out.print("{}");
        }
    }
}
