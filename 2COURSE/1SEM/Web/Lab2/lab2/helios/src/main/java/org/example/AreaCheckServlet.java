package org.example;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 */
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class AreaCheckServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final List<Double> VALID_R_VALUES = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String x_str = request.getParameter("x");
        String y_str = replaceCommaWithDot(request.getParameter("y"));
        String r_str = request.getParameter("r");
        String timezoneOffset_str = request.getParameter("timezoneOffset");

        List<String> errors = new ArrayList<>();

        Instant nowUtc = Instant.now();
        LocalDateTime currentTimeUtc = LocalDateTime.ofInstant(nowUtc, ZoneOffset.UTC);

        if (x_str == null || y_str == null || r_str == null || timezoneOffset_str == null) {
            errors.add("Not all parameters provided");
            response.getWriter().write("Error: " + String.join(", ", errors));
            response.getWriter().flush();
            return;
        } else if (isInvalidNumber(x_str) || isInvalidNumber(y_str) || isInvalidNumber(r_str) || isInvalidNumber(timezoneOffset_str)) {
            errors.add("Data is not correct");
            response.getWriter().write("Error: " + String.join(", ", errors));
            response.getWriter().flush();
            return;
        }

        double x = Double.parseDouble(x_str);
        double y = Double.parseDouble(y_str);
        double r = Double.parseDouble(r_str);

        if (!(x >= -3 && x <= 5) || !(y >= -5 && y <= 5) || !(VALID_R_VALUES.contains(r))) {
            errors.add("Numbers beyond the limit");
            response.getWriter().write("Error: " + String.join(", ", errors));
            response.getWriter().flush();
            return;
        }

        long startTime = System.nanoTime();
        boolean hit = checkHit(x, y, r);
        double executionTime = (System.nanoTime() - startTime) / 1_000_000.0;

        int timezoneOffset = Integer.parseInt(timezoneOffset_str);
        LocalDateTime adjustedCurrentTime = currentTimeUtc.minusMinutes(timezoneOffset);

        Result result = new Result(x, y, r, hit, executionTime, adjustedCurrentTime);

        List<Result> results = (List<Result>) request.getSession().getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
            request.getSession().setAttribute("results", results);
        }
        results.add(0, result);

        String resultHtml = "<tr>" +
                "<td>" + String.format("%.2f", result.getX()) + "</td>" +
                "<td>" + String.format("%.2f", result.getY()) + "</td>" +
                "<td>" + String.format("%.2f", result.getR()) + "</td>" +
                "<td>" + (result.isHit() ? "Hit" : "Miss") + "</td>" +
                "<td>" + result.getCurrentTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "</td>" +
                "<td>" + String.format("%.5f", result.getExecutionTime()) + " ms" + "</td>" +
                "</tr>";
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(resultHtml);
        response.getWriter().flush();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> errors = new ArrayList<>();
        errors.add("Post not allowed!");
        response.getWriter().write("Error: " + String.join(", ", errors));
        response.getWriter().flush();
    }

    private boolean isInvalidNumber(String value) {
        if (value == null || value.trim().isEmpty()) {
            return true;
        }
        value = value.trim();
        if (value.split(",").length > 1) {
            return true;
        }

        try {
            Double.parseDouble(value);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    private boolean checkHit(double x, double y, double r) {
        return (x >= -r / 2) && (x <= 0) && (y >= -r) && (y <= 0) ||
                (x >= 0) && (x <= r) && (y <= r) && (y >= 0) && (x * x + y * y <= r * r) ||
                (x >= -r) && (x <= 0) && (y >= 0) && (y <= r) && (y <= x + r);
    }
    private String replaceCommaWithDot(String value) {
        if (value == null) {
            return null;
        }
        int commaCount = value.length() - value.replace(",", "").length();
        if (commaCount > 1) {
            return null;
        }
        return value.replace(",", ".");
    }

}
