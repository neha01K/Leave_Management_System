package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/HttpSessionDemo")
public class HttpSessionDemo extends HttpServlet {

    public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {

        int number = Integer.parseInt(httpRequest.getParameter("number"));
        HttpSession session = httpRequest.getSession();
        session.setAttribute("key", number);
        httpResponse.sendRedirect("HttpSessionServlet2");
    }
}
