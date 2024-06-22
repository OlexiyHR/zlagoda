package ua.training.controller.command.auth;

import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SessionManager.getInstance().invalidateSession(request.getSession());
		RedirectionManager.getInstance().redirect(new HttpWrapper(request, response), ServletPath.HOME);
		return RedirectionManager.REDIRECTION;
	}
}
