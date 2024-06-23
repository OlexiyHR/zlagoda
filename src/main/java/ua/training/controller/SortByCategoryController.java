package ua.training.controller;


import org.apache.log4j.Logger;
import ua.training.constants.Attribute;
import ua.training.controller.command.Command;
import ua.training.controller.command.i18n.AppLocale;
import ua.training.controller.filter.FilterAccess;
import ua.training.controller.utils.CommandKeyGenerator;
import ua.training.controller.utils.HttpWrapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Objects;
import java.util.Optional;

/**
 * Application HTTP Front Servlet that processes all the incoming requests
 */



@WebServlet(urlPatterns = { "/sort-by-category" }, loadOnStartup = 1)
public class SortByCategoryController extends HttpServlet {

    Connection connection;
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);
    private static final long serialVersionUID = 1L;

    public SortByCategoryController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (FilterAccess.getInstance().filterAccess(request, "Касир")) {
            request.getRequestDispatcher("/WEB-INF/views/sortByCategoryInput.jsp").forward(request, response);
        } else if (FilterAccess.getInstance().filterAccess(request, "Менеджер")) {
            response.sendRedirect("/manager");
        } else response.sendRedirect("/login");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(request, response);
        String commandKey = CommandKeyGenerator.generateCommandKeyFromRequest(request);
        Command command = CommandFactory.getCommand(commandKey);
        try {
            String commandResultedResource = command.execute(request, response);
            if(Objects.equals(commandResultedResource, "")) response.sendRedirect("/sort-by-category");
            request.setAttribute("htmlTable", commandResultedResource);
            request.getRequestDispatcher("/WEB-INF/views/Results.jsp").forward(request, response);


        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
