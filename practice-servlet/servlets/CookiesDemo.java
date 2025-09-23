//comments in this code are for my practice purpose

package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CookiesDemo extends HttpServlet {

    /*public void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException {

        String name = httpRequest.getParameter("name");

        Cookie cookie = new Cookie("username", name);

        httpResponse.addCookie(cookie);

        PrintWriter printWriter = httpResponse.getWriter();
        printWriter.println("Hey "+cookie.getValue() +"! Your Cookie is added");
        printWriter.println("Welcome "+ cookie.getValue() +"!");
    }

    public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException{

        httpResponse.setContentType("text/html");

        PrintWriter printWriter = httpResponse.getWriter();

        Cookie[] cookies = httpRequest.getCookies();
        boolean isUserVisited = false;

        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("username")){
                    printWriter.println("<h2>Welcome Back "+cookie.getValue()+"!</h2>");
                    isUserVisited = true;
                }
            }
        }

        if(!isUserVisited){
            printWriter.println("<h3>Looks like you never visited. Go back and enter your name</h3>");
            printWriter.print("<a href='index.jsp'> <button>Go Back</button> </a>");
        }
    }*/

    public void service(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, ServletException{

        httpResponse.setContentType("text/html");
        String name = httpRequest.getParameter("name");

        PrintWriter printWriter = httpResponse.getWriter();

        Cookie[] cookies = httpRequest.getCookies();

        boolean isUserVisited = false;
        if(cookies!=null){
            for(Cookie cookieObj : cookies){
                if(cookieObj.getName().equals("username") && cookieObj.getValue().equals(name)){
                    printWriter.println("Welcome back "+cookieObj.getValue());
                    isUserVisited = true;
                }
            }
        }

        if(!isUserVisited){
            Cookie cookie = new Cookie("username",name);
            httpResponse.addCookie(cookie);
            printWriter.println("Hey "+cookie.getValue()+"!");
        }
    }

}
