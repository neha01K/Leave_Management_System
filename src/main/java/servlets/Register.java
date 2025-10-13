package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import com.lms.queries.QueriesConstant;
import com.lms.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;
import java.time.LocalDate;
import com.lms.queries.QueriesConstant;

@WebServlet("/Register")
public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String name  = request.getParameter("registerEmployeeName");
            String email = request.getParameter("registerEmail");
            String designation = request.getParameter("registerDesignation");
            LocalDate joiningDate = LocalDate.parse(request.getParameter("registerJoiningDate"));

            try(Connection connection = DBConnection.getConnection()) {

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(QueriesConstant.COUNT_EMPLOYEE);
                resultSet.next();

                int employeeCount = resultSet.getInt(1);

                String employeeID = "EMP" +  String.format("%03d", employeeCount+1);

                PreparedStatement preparedStatement = connection.prepareStatement(QueriesConstant.REGISTER_EMPLOYEE);

                preparedStatement.setString(1,employeeID);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, designation);
                preparedStatement.setString(4, email);
                preparedStatement.setDate(5,Date.valueOf(joiningDate) );


                preparedStatement.executeUpdate();

                out.println(employeeID);
                out.println(name);

                PreparedStatement preparedStatementForLogin = connection.prepareStatement(QueriesConstant.INSERT_EMPLOYEE_INTO_LOGIN);
                preparedStatementForLogin.setString(1, employeeID);
                preparedStatementForLogin.executeUpdate();

                PreparedStatement balanceStatement = connection.prepareStatement(QueriesConstant.LEAVEBALANCE_INITIALIZATION);
                balanceStatement.setString(1, employeeID);
                balanceStatement.executeUpdate();
            }

            catch(Exception exception){
                exception.printStackTrace();
                out.println("ERROR");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Employee Details(registration) servlet";
    }
}