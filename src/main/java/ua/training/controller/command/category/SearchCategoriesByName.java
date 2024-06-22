package ua.training.controller.command.category;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Category;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCategoriesByName implements Command {

	private final CategoryService categoryService;

	public SearchCategoriesByName(CategoryService userService) {
		this.categoryService = userService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter(Attribute.NAME);
		List<String> errors = validateUserInput(name);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_CATEGORIES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<Category> categories = categoryService.searchCategoriesByName(name);

		if (categories.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.CATEGORY_IS_NOT_FOUND);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_CATEGORIES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.CATEGORIES, categories);
		return Page.ALL_CATEGORIES_VIEW;
	}

	private List<String> validateUserInput(String name) {
		List<String> errors = new ArrayList<>();

		AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.NAME, name, errors);
		return errors;
	}
}
