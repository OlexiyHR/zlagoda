package ua.training.controller.command.auth;

import ua.training.controller.command.Command;
import ua.training.controller.utils.SessionManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PostLogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionManager.getInstance().invalidateSession(request.getSession());
        return "";
    }
}
