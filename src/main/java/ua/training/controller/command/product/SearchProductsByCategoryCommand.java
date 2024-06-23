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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductsByCategoryCommand implements Command {

	private final ProductService productService;
	private final CategoryService categoryService;

	public SearchProductsByCategoryCommand(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String category = request.getParameter(Attribute.CATEGORY);
		List<String> errors = validateUserInput(category);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			return "";
		}

		StringBuilder products = productService.searchDishesByCategoryName(category);

		if (countRows(products.toString()) < 2) {
			return "No such product or category";
		}


		return products.toString();
	}

	private List<String> validateUserInput(String category) {
		List<String> errors = new ArrayList<>();

		if (category.isEmpty()) {
			errors.add(Message.INVALID_CATEGORY);
		}

		return errors;
	}

	private int countRows(String htmlTable) {
		int rowCount = 0;
		String[] rows = htmlTable.split("</tr>");
		for (String row : rows) {
			if (row.contains("<tr>")) {
				rowCount++;
			}
		}
		return rowCount;
	}
}
