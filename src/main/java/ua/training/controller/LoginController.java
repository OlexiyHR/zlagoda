package ua.training.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.command.i18n.AppLocale;
import ua.training.controller.utils.CommandKeyGenerator;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.User;
import ua.training.exception.ServiceException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

/**
 * Application HTTP Front Servlet that processes all the incoming requests
 */



@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final long serialVersionUID = 1L;

    public LoginController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
        processRequest(req, resp);
    }

    /**
     * Processes all the requests by proper concrete command that implements
     * {@link Command} interface depends on the request path
     *
     * @param request
     *            incoming request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpWrapper httpWrapper = new HttpWrapper(request, response);
        String commandKey = CommandKeyGenerator.generateCommandKeyFromRequest(request);
        Command command = CommandFactory.getCommand(commandKey);
        try {
            String commandResultedResource = command.execute(request, response);
            if (commandResultedResource.equals("Менеджер")){
                response.sendRedirect("/manager");
            } else if (commandResultedResource.equals("Касир")){
                request.getRequestDispatcher("/cashier/").forward(request, response);
            }
        } catch (ServiceException ex) {
            LOGGER.error("Error has occured while command execution with key: " + commandKey);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}