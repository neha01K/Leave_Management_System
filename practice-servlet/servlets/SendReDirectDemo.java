package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class SendReDirectDemo extends HttpServlet {
    public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        int number = Integer.parseInt(httpRequest.getParameter("key"));
        int square = number*number;
        PrintWriter printWriter = httpResponse.getWriter();
        printWriter.println("Square of number: "+square);
    }
}
