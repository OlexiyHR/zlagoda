package ua.training.dao;

import ua.training.entity.Dish;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public interface ProductDao extends GenericDao<Dish, Long>, AutoCloseable {

	StringBuilder searchProductsByName(String name);

	StringBuilder searchDishByCategoryName(String categoryName);

	List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate);

	void close();
}
