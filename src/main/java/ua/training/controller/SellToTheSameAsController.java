package ua.training.controller;


import org.apache.log4j.Logger;
import ua.training.constants.Attribute;
import ua.training.controller.command.Command;
import ua.training.controller.command.i18n.AppLocale;
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
import java.util.Optional;

/**
 * Application HTTP Front Servlet that processes all the incoming requests
 */



@WebServlet(urlPatterns = { "/show-same-clients" }, loadOnStartup = 1)
public class SellToTheSameAsController extends HttpServlet {

    Connection connection;
    private static final Logger LOGGER = Logger.getLogger(FrontController.class);
    private static final long serialVersionUID = 1L;

    public SellToTheSameAsController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("<table border='1'>");
        htmlTable.append("<tr><th>id_employee</th><th>empl_surname</th><th>empl_name</th></tr>");

        String query = "SELECT id_employee, empl_surname, empl_name " +
                "FROM employee AS empl " +
                "WHERE id_employee IN (SELECT id_employee " +
                "              FROM bcheck " +
                "              WHERE NOT EXISTS ( SELECT card_number " +
                "                            FROM bcheck " +
                "                            WHERE card_number NOT IN ( SELECT card_number " +
                "                                            FROM bcheck " +
                "                                            WHERE id_employee = empl.id_employee) " +
                "                             AND id_employee IN ( SELECT id_employee " +
                "                                             FROM employee " +
                "                                             WHERE empl_surname = (?) AND empl_name = (?)" +
                "                               )" +
                "                 )" +
                "      );";

        Connection connection = null;
        PreparedStatement prepquery = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "ol12345");
            prepquery = connection.prepareStatement(query);
            prepquery.setString(1, surname);
            prepquery.setString(2, name);

            resultSet = prepquery.executeQuery();
            while (resultSet.next()) {
                htmlTable.append("<tr>");
                htmlTable.append("<td>").append(resultSet.getString("id_employee")).append("</td>");
                htmlTable.append("<td>").append(resultSet.getString("empl_surname")).append("</td>");
                htmlTable.append("<td>").append(resultSet.getString("empl_name")).append("</td>");
                htmlTable.append("</tr>");
            }
            htmlTable.append("</table>");

            request.setAttribute("htmlTable", htmlTable.toString());
            request.getRequestDispatcher("/WEB-INF/views/Results.jsp").forward(request, response);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (prepquery != null) prepquery.close();
                if (connection != null) connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/sellsame.jsp").forward(request, response);
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
            System.out.println("Generated HTML Table: " + commandResultedResource);
            request.setAttribute("htmlTable", commandResultedResource);
            request.getRequestDispatcher("/WEB-INF/views/ResultNumbers.jsp").forward(request, response);


        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}