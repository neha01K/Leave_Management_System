package servlets;

import com.lms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Login")
public class Login extends HttpServlet{

    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {

        try (PrintWriter out = httpResponse.getWriter()) {
            String employeeID = httpRequest.getParameter("employeeID");
            String employeeLoginPassword = httpRequest.getParameter("employeeLoginPassword");

            try(Connection connection = DBConnection.getConnection()) {


                String query = "SELECT employee.employeeName FROM employees employee " +
                               "JOIN loginDetails login "+
                               "ON employee.employeeId = login.employeeID "+
                               "WHERE employee.employeeId=? AND login.password=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, employeeID);
                preparedStatement.setString(2, employeeLoginPassword);

                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){

                    HttpSession session  = httpRequest.getSession(true);
                    session.setAttribute("employeeID", employeeID);
                    session.setAttribute("employeeName", resultSet.getString("employeeName"));
                    session.setAttribute("authenticated", true);

                    session.setMaxInactiveInterval(5 * 60);

                    out.println("SUCCESS");
                }
                else{
                    out.println("INVALID");
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
                out.println("ERROR");
            }
        }
    }
    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException,IOException{
        httpResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED,
                "GET method is not supported for login");
    }

}
