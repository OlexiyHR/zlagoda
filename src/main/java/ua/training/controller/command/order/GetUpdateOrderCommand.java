package ua.training.controller.command.order;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Order;
import ua.training.entity.Status;
import ua.training.service.ProductService;
import ua.training.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class GetUpdateOrderCommand implements Command {

	private final OrderService orderService;
	private final ProductService productService;

	public GetUpdateOrderCommand(OrderService orderService, ProductService productService) {
		this.orderService = orderService;
		this.productService = productService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long orderId = Long.parseLong(request.getParameter(Attribute.ID_ORDER));

		Optional<Order> order = orderService.getOrderById(orderId);

		request.setAttribute(Attribute.STATUSES, Status.values());		
		request.setAttribute(Attribute.DISHES, productService.getAllProducts());
		request.setAttribute(Attribute.ORDER, order.get());
		return Page.ADD_UPDATE_ORDER_VIEW;
	}
}
