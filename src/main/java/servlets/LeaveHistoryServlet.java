package servlets;

import com.lms.util.DBConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.lms.util.SessionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LeaveHistory")
public class LeaveHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String employeeID = SessionUtils.checkLoggedInEmployee(request, response);
        if(employeeID==null)
            return;

        //response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try (Connection connection = DBConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM leave_history WHERE employeeID=?"
            );
            preparedStatement.setString(1, employeeID);
            ResultSet resultSet = preparedStatement.executeQuery();

            JSONArray jsonArray = new JSONArray();
            while (resultSet.next()) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("leaveType", resultSet.getString("leaveType"));
                jsonObj.put("leaveStartDate", resultSet.getString("leaveStartDate"));
                jsonObj.put("leaveEndDate", resultSet.getString("leaveEndDate"));
                jsonObj.put("leaveReason", resultSet.getString("leaveReason"));
                jsonArray.put(jsonObj);
            }
            out.print(jsonArray.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
            out.print("[]");
        }
    }
}
