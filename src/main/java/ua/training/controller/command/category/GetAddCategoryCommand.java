package ua.training.controller.command.category;

import ua.training.constants.Page;
import ua.training.controller.command.Command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetAddCategoryCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return Page.ADD_UPDATE_CATEGORY_VIEW;
	}
}
