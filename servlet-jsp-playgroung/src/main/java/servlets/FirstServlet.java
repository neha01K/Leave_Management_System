package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/first")
public class FirstServlet implements Servlet{

    private ServletConfig servletconfig;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        servletConfig.getServletContext().log("Initializing Servlet");
        this.servletconfig = servletConfig;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Service Requested");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet Destroyed");
    }

    @Override
    public ServletConfig getServletConfig() {
        return this.servletconfig;
    }


    @Override
    public String getServletInfo() {
        return "This servlet is created by Neha!";
    }

}
