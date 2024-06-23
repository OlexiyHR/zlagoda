package ua.training.controller.command.product;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.locale.Message;
import ua.training.service.CategoryService;
import ua.training.service.ProductService;
import ua.training.validator.field.AbstractFieldValidatorHandler;
import ua.training.validator.field.FieldValidatorKey;
import ua.training.validator.field.FieldValidatorsChainGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductsByNameCommand implements Command {

	private final ProductService productService;
	private final CategoryService categoryService;

	public SearchProductsByNameCommand(ProductService productService, CategoryService categoryService) {
		this.productService = productService;
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		String name = request.getParameter(Attribute.NAME);
		List<String> errors = validateUserInput(name);
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams;

		if (!errors.isEmpty()) {
			return "";
		}

		StringBuilder products = productService.searchProductsByName(name);

		if (countRows(products.toString()) < 2) {
			return "No such product";
		}


		return products.toString();
	}

	private List<String> validateUserInput(String name) {
		List<String> errors = new ArrayList<>();

		AbstractFieldValidatorHandler fieldValidator = FieldValidatorsChainGenerator.getFieldValidatorsChain();
		fieldValidator.validateField(FieldValidatorKey.NAME, name, errors);
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
