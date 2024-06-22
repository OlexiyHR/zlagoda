package ua.training.controller.command.dish;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Dish;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.DishService;
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

public class SearchDishesByNameCommand implements Command {

	private final DishService dishService;
	private final CategoryService categoryService;

	public SearchDishesByNameCommand(DishService dishService, CategoryService categoryService) {
		this.dishService = dishService;
		this.categoryService = categoryService;
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
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<Dish> dishes= dishService.searchDishesByName(name);

		if (dishes.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.DISH_IS_NOT_FOUND);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.CATEGORIES, categoryService.getAllCategories());
		request.setAttribute(Attribute.DISHES, dishes);
		return Page.ALL_DISHES_VIEW;
	}

	private List<String> validateUserInput(String name) {
		List<String> errors = new ArrayList<>();

		AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.NAME, name, errors);
		return errors;
	}
}
