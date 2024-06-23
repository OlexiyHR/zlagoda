package ua.training.controller.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.User;

public class FilterAccess {

    public static FilterAccess getInstance() { return new FilterAccess();}

    public boolean filterAccess(HttpServletRequest request, String required) {
        HttpSession session = request.getSession();
        User user = SessionManager.getInstance().getUserFromSession(session);
        if (user == null) return false;
        return (user.getRole().getValue().equals(required));
    }
}
