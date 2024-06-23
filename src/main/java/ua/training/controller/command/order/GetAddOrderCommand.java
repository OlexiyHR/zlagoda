package ua.training.controller.command.order;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetAddOrderCommand implements Command {

	private final ProductService productService;

	public GetAddOrderCommand(ProductService productService) {
		this.productService = productService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setAttribute(Attribute.DISHES, productService.getAllProducts());
		return Page.ADD_UPDATE_ORDER_VIEW;
	}
}
