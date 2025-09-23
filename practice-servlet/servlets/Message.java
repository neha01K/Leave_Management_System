//comments in this code are for my practice purpose

package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Message extends HttpServlet {


    /*public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        int number1 = Integer.parseInt(httpRequest.getParameter("number1"));
        int number2 = Integer.parseInt(httpRequest.getParameter("number2"));

        PrintWriter printWriter =  httpResponse.getWriter();

        int sum = number1+number2;
        printWriter.print("Sum of "+number1+ "and "+number2 +" is "+sum);
    }*/

    /*public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        int value1 = Integer.parseInt(httpRequest.getParameter("number1"));
        int value2 = Integer.parseInt(httpRequest.getParameter("number2"));
        PrintWriter printWriter =  httpResponse.getWriter();

        int sum = value1+value2;
        printWriter.print("Sum of "+value1+ "and "+value2 +" is "+sum);
    }*/

    public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {
        int value1 = Integer.parseInt(httpRequest.getParameter("number1"));
        int value2 = Integer.parseInt(httpRequest.getParameter("number2"));
        PrintWriter printWriter =  httpResponse.getWriter();

        int sum = value1+value2;
       /* RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("RequestDispatcherDemo");

        httpRequest.setAttribute("SumKey", sum);

        requestDispatcher.forward(httpRequest, httpResponse);
        requestDispatcher.include(httpRequest, httpResponse);*/

        //url-rewriting
        httpResponse.sendRedirect("SendReDirectDemo?key="+sum);

    }

}
