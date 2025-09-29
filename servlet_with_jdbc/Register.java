package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/Register")
public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String name  = request.getParameter("user_name");
            String email = request.getParameter("user_email");
            String password = request.getParameter("user_password");

            try{
                Thread.sleep(3000);

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registration","root", "Hitmanbau1*@sql");

                String query = "insert into user(username, userEmail, userPassword) values(?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                preparedStatement.executeUpdate();

                out.println("Done");

            }
            catch(Exception exception){
                exception.printStackTrace();
                out.println("<h1>Error in saving data to database!</h1>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}