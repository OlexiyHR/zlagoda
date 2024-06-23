package ua.training.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Basic interface for Command GoF Pattern HTTP requests execution realization
 * <p>
 * Class that performs HTTP request execution have to implement this interface
 * 
 * @author Solomka
 *
 */
public interface Command {
	
	/**
	 * method that performs execution of the client request
	 * 
	 * @param request
	 *            client request
	 * @param response
	 *            response to client request
	 * 
	 * @return String logic name of the response jsp page
	 * @throws ServletException
	 * @throws IOException
	 */
	String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, SQLException;
}
