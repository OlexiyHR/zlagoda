package ua.training.controller.command.order;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.constants.ServletPath;
import ua.training.controller.command.Command;
import ua.training.controller.utils.HttpWrapper;
import ua.training.controller.utils.RedirectionManager;
import ua.training.controller.utils.SessionManager;
import ua.training.entity.Dish;
import ua.training.entity.Order;
import ua.training.entity.Status;
import ua.training.entity.User;
import ua.training.locale.Message;
import ua.training.service.DishService;
import ua.training.service.OrderService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class PostAddOrderCommand implements Command {

	private final OrderService orderService;
	private final DishService dishService;

	public PostAddOrderCommand(OrderService orderService, DishService dishService) {
		this.orderService = orderService;
		this.dishService = dishService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<String> errors = validateUserInput(request.getParameterValues(Attribute.DISHES));

		if (errors.isEmpty()) {
			Order order = generateOrder(request);
			orderService.createOrder(order);
			redirectToAllOrdersPageWithSuccessMessage(request, response);
			return RedirectionManager.REDIRECTION;
		}

		request.setAttribute(Attribute.ERRORS, errors);
		request.setAttribute(Attribute.DISHES, dishService.getAllDishes());
		return Page.ADD_UPDATE_ORDER_VIEW;
	}

	private List<String> validateUserInput(String[] dishesIds) {
		List<String> errors = new ArrayList<String>();

		if (dishesIds == null) {
			errors.add(Message.INVALID_DISHES);
		}

		return errors;
	}

	private Order generateOrder(HttpServletRequest request) {
		List<Dish> orderDishes = getOrderDishes(request.getParameterValues(Attribute.DISHES));
		return new Order.Builder().setDishes(orderDishes).setDate(LocalDateTime.now()).setStatus(Status.NEW)
				.setUser(getUserFromSession(request)).setTotal(getOrderTotal(orderDishes)).build();
	}

	private List<Dish> getOrderDishes(String[] dishesIds) {
		return Arrays.stream(dishesIds).map(dishId -> dishService.getDishById(Long.parseLong(dishId)).get())
				.collect(Collectors.toList());
	}

	private User getUserFromSession(HttpServletRequest request) {
		return SessionManager.getInstance().getUserFromSession(request.getSession());
	}

	private BigDecimal getOrderTotal(List<Dish> dishes) {
		return dishes.stream().map(Dish::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private void redirectToAllOrdersPageWithSuccessMessage(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpWrapper httpWrapper = new HttpWrapper(request, response);
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put(Attribute.SUCCESS, Message.SUCCESS_ORDER_ADDITION);
		RedirectionManager.getInstance().redirectWithParams(httpWrapper, ServletPath.ALL_ORDERS, urlParams);
	}
}
