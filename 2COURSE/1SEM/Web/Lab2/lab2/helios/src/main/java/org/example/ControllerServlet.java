package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String x_str = request.getParameter("x");
        String y_str = request.getParameter("y");
        String r_str = request.getParameter("r");

        if (x_str != null && y_str != null && r_str != null) {
            request.getRequestDispatcher("/areaCheck").forward(request, response);
        }else {
            response.setContentType("text/html;charset=UTF-8");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> errors = new ArrayList<>();
        errors.add("Post not allowed!");
        response.getWriter().write("Error: " + String.join(", ", errors));
        response.getWriter().flush();
    }



}
