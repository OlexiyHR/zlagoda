package ua.training.controller.command.product;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.entity.Dish;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchMostPopularDishesPerPeriodCommand implements Command {

	private final ProductService productService;
	private final CategoryService categoryService;

	public SearchMostPopularDishesPerPeriodCommand(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fromDate = request.getParameter(Attribute.FROM_DATE);
		String toDate = request.getParameter(Attribute.TO_DATE);

		List<String> errors = validateUserInput(fromDate, toDate);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, errors.get(0));
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		List<Dish> dishes = productService.searchMostPopularDishesPerPeriod(LocalDate.parse(fromDate),
				LocalDate.parse(toDate));

		if (dishes.isEmpty()) {
			urlParams = new HashMap<>();
			urlParams.put(Attribute.ERROR, Message.DISH_IS_NOT_FOUND);
			RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_DISHES, urlParams);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.DISHES, dishes);
		request.setAttribute(Attribute.CATEGORIES, categoryService.getAllCategories());
		return Page.ALL_DISHES_VIEW;
	}

	private List<String> validateUserInput(String fromDate, String toDate) {
		List<String> errors = new ArrayList<>();
		
		if(fromDate.isEmpty() || toDate.isEmpty() || LocalDate.parse(fromDate).compareTo(LocalDate.parse(toDate)) > 0 ) {
			errors.add(Message.INVALID_DATE);
		}

		return errors;
	}
}
