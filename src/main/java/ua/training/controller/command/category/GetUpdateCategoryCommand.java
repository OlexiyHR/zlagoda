package ua.training.controller.command.category;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Category;
import ua.training.service.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GetUpdateCategoryCommand implements Command {

	private final CategoryService categoryService;

	public GetUpdateCategoryCommand(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long categoryId = Long.parseLong(request.getParameter(Attribute.ID_CATEGORY));

		Optional<Category> category = categoryService.getCategoryById(categoryId);
		request.setAttribute(Attribute.CATEGORY, category.get());

		return Page.ADD_UPDATE_CATEGORY_VIEW;
	}
}
