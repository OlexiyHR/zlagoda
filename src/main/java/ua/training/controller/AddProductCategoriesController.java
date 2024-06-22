package ua.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Application HTTP Front Servlet for adding new employee
 */

@WebServlet(urlPatterns = { "/product-categories" }, loadOnStartup = 1)
public class AddProductCategoriesController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddProductCategoriesController() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementing doGet is not required for an empty container
        // If needed, you can handle any initializations or redirects here
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implementing doPost is not required for an empty container
        // If needed, you can handle any POST requests here
    }
}