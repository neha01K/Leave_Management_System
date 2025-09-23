package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RequestDispatcherDemo extends HttpServlet {

    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        PrintWriter printWriter = httpResponse.getWriter();

        printWriter.println("Dispatcher is working");

        int sumOfTwoValues = (int)httpRequest.getAttribute("SumKey");
        printWriter.print("Sum of two Values: "+ sumOfTwoValues);
    }
}
