package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

//@WebServlet("/third")
public class ThirdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("proccessing do get request by third servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        System.out.println("Form submitted using doPost method");
        String message = request.getParameter("message");
        System.out.println("Message: " + message);

        response.setContentType("text/html");
        PrintWriter printWriter = response.getWriter();
        printWriter.println("<h1>Form submitted<h1>");
        printWriter.println("<p>Message: " + message + "<p>");

        Date date = new Date();
        printWriter.print("""

                <h2>Message : %s</h2>
                <h3>Current Date: %s</h3>

                """.formatted(message,date));

        response.setStatus(401);


    }
}
