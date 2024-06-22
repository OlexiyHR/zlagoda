package ua.training.controller.command.category;

import ua.training.constants.Attribute;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.service.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteCategoryCommand implements Command {

	private final CategoryService categoryService;

	public DeleteCategoryCommand(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long categoryId = Long.parseLong(request.getParameter(Attribute.ID_CATEGORY));

		categoryService.deleteCategory(categoryId);

		redirectToAllCategoriesPageWithSuccessMessage(request, response);
		return RedirectionManager.REDIRECTION;
	}

	private void redirectToAllCategoriesPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_CATEGORY_DELETE);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_CATEGORIES, urlParams);
	}
}
