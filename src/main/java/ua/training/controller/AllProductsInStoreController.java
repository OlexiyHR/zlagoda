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
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Objects;

@WebServlet("/all-products-in-store")
public class AllProductsInStoreController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LoginController.class);
    private static final long serialVersionUID = 1L;

    public AllProductsInStoreController() {
    }

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(Attribute.LOCALES, AppLocale.getAppLocales());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (FilterAccess.getInstance().filterAccess(request, "Касир")) {
            StringBuilder htmlTable = new StringBuilder();
            htmlTable.append("<table border='1'>");
            htmlTable.append("<tr><th>Product_name</th><th>UPC</th><th>Price</th><th>Quantity</th></tr>");

            Connection connection = null;
            ResultSet resultSet = null;
            String query = "SELECT * FROM product JOIN store_product USING(id_product) ORDER BY product_name";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "ol12345");
                Statement query1 = connection.createStatement();
                resultSet = query1.executeQuery(query);
                while (resultSet.next()) {
                    htmlTable.append("<tr>");
                    htmlTable.append("<td>").append(resultSet.getString("product_name")).append("</td>");
                    htmlTable.append("<td>").append(resultSet.getString("UPC")).append("</td>");
                    htmlTable.append("<td>").append(resultSet.getString("selling_price")).append("</td>");
                    htmlTable.append("<td>").append(resultSet.getString("product_number")).append("</td>");
                    htmlTable.append("</tr>");
                }
                htmlTable.append("</table>");

                request.setAttribute("htmlTable", htmlTable.toString());
                request.getRequestDispatcher("/WEB-INF/views/AllProductsInStore.jsp").forward(request, response);

            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (resultSet != null) resultSet.close();
                    if (connection != null) connection.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
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
            if(Objects.equals(commandResultedResource, "")) response.sendRedirect("/all-products");
            else {
                request.setAttribute("htmlTable", commandResultedResource);
                request.getRequestDispatcher("/WEB-INF/views/AllProducts.jsp").forward(request, response);
            }
        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}