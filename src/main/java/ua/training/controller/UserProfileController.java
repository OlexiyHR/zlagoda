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
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Application HTTP Front Servlet that processes all the incoming requests
 */



@WebServlet("/user-profile")
public class UserProfileController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final long serialVersionUID = 1L;

    public UserProfileController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (FilterAccess.getInstance().filterAccess(request, "Менеджер")) {
            processRequest(request, response);
        }
        else if (FilterAccess.getInstance().filterAccess(request, "Касир")){
            processRequest(request, response);
        }
        else response.sendRedirect("/login");
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(request, response);
        HttpSession session = request.getSession();
        User user = SessionManager.getInstance().getUserFromSession(session);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Формування рядка з даними користувача
        StringBuilder userProfileInfo = new StringBuilder();
        userProfileInfo.append(user.getName()).append(" ")
                .append(user.getSurname()).append(", ")
                .append(user.getPathronymic()).append(", ")
                .append(user.getEmail()).append(", ")
                .append(user.getPhone()).append(", ")
                .append(user.getSalary()).append(", ")
                .append(dateFormat.format(user.getBirth())).append(", ")
                .append(dateFormat.format(user.getStart())).append(", ")
                .append(user.getRole()).append(", ")
                .append(user.getCity()).append(", ")
                .append(user.getStreet()).append(", ")
                .append(user.getZip()).append(", ")
                .append(user.getPassword());

        // Передача цього рядка як атрибуту в запит
        request.setAttribute("UserProfile", userProfileInfo.toString());
        request.getRequestDispatcher("/WEB-INF/views/UserProfile.jsp").forward(request, response);
    }

}
