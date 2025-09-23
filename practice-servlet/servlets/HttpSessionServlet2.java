package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/HttpSessionServlet2")
public class HttpSessionServlet2 extends HttpServlet {

    public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {

       HttpSession session = httpRequest.getSession();

       int number = (Integer)session.getAttribute("key");
       int square = number*number;

       PrintWriter printWriter = httpResponse.getWriter();

       printWriter.println("Square of the number: "+square);

    }
}
