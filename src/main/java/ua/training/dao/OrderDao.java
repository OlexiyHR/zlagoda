package ua.training.dao;

import ua.training.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long>, AutoCloseable {

	void saveOrderDishes(Order order);

	StringBuilder searchWaiterOrdersForToday(Long idWaiter, LocalDate date);

	List<Order> searchUpreparedOrdersForToday(LocalDate date);

	List<Order> searchOrdersByDate(LocalDate fromDate, LocalDate toDate);

	void close();
}
