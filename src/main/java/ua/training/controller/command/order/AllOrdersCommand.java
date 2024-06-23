package ua.training.controller.command.order;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.Order;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AllOrdersCommand implements Command {

	private final OrderService orderService;

	public AllOrdersCommand(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User loggedInUser = SessionManager.getInstance().getUserFromSession(request.getSession());
		StringBuilder orders = new  StringBuilder();
		List<Order> aa = new ArrayList<Order>();
		if (loggedInUser.getRole().equals(Role.CASHIER)) {
		} else if (loggedInUser.getRole().equals(Role.MANAGER)) {
			orders = orderService.getAllOrders();
		}

		request.setAttribute(Attribute.ORDERS, orders);
		return Page.ALL_ORDERS_VIEW;
	}
}
