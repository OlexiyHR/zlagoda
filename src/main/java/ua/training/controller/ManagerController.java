package ua.training.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.command.i18n.AppLocale;
import ua.training.controller.filter.FilterAccess;
import ua.training.controller.utils.CommandKeyGenerator;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.User;
import ua.training.exception.ServiceException;
import ua.training.controller.filter.FilterAccess;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Application HTTP Front Servlet that processes all the incoming requests
 */



@WebServlet("/manager")
public class ManagerController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final long serialVersionUID = 1L;

    public ManagerController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (FilterAccess.getInstance().filterAccess(request, "Менеджер")) {
            request.getRequestDispatcher("/WEB-INF/views/manager.jsp").forward(request, response);
        }
        else if (FilterAccess.getInstance().filterAccess(request, "Касир")){
            response.sendRedirect("/cashier");
        }
        else response.sendRedirect("/login");
    }

}