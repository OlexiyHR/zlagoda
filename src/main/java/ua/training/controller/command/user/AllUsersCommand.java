package ua.training.controller.command.user;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AllUsersCommand implements Command {

	private final UserService userService;

	public AllUsersCommand(UserService userService) {
		this.userService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder users = userService.getAllUsers();
		
		request.setAttribute(Attribute.USERS, users);
		request.setAttribute(Attribute.ROLES, Role.values());
		return Page.ALL_USERS_VIEW;
	}
}
