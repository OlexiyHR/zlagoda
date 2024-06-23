package ua.training.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.dao.ProductDao;
import ua.training.entity.Dish;
import ua.training.exception.ServerException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcProductDao.class);

	private static String GET_ALL = "SELECT * FROM product JOIN category USING(category_number) ORDER BY product_name";
	private static String GET_BY_ID = "SELECT * FROM dish JOIN category USING(id_category) WHERE id_dish=?";
	private static String CREATE = "INSERT INTO dish (name, description, weight, cost, id_category) VALUES (?, ?, ?, ?, ?)";
	private static String UPDATE = "UPDATE dish SET name=?, description=?, weight=?, cost=?, id_category=? WHERE id_dish=?";
	private static String DELETE = "DELETE FROM dish WHERE id_dish=?";
	private static String SEARCH_PRODUCT_BY_NAME = "SELECT * FROM product JOIN category USING(category_number) WHERE LOWER(product_name) LIKE CONCAT('%', LOWER(?), '%')";
	private static String SEARCH_PRODUCT_BY_CATEGORY_NAME = "SELECT * FROM product JOIN category USING(category_number) WHERE category_name=?";
	private static String SEARCH_MOST_POPULAR_DISHES_IN_PERIOD = 
			"SELECT *"
					+ " FROM dish JOIN category USING(id_category)" 
					+ " WHERE id_dish IN (SELECT order_item.id_dish"
										+ " FROM `order` JOIN order_item USING(id_order)"
										+ " WHERE `date` BETWEEN ? AND ?"
										+ " GROUP BY id_dish" 
										+ "	HAVING COUNT(order_item.id_order)=(SELECT MAX(orders_number)"
																				+ "	FROM (SELECT COUNT(order_item.id_order)AS orders_number"
																				+ "	FROM `order` JOIN order_item USING(id_order)"
																				+ " WHERE `date` BETWEEN ? AND ?"
																				+ "	GROUP BY id_dish )AS `orders_counter`))";
	
	// table columns names
	private static String ID = "id_product";
	private static String NAME = "product_name";
	private static String CHARACTERISTICS = "characteristics";
	private static String CATEGORY_NAME = "category_name";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcProductDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcProductDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public StringBuilder getAll() {
		StringBuilder htmlTable = new StringBuilder();

		try (Statement query = connection.createStatement(); ResultSet rs = query.executeQuery(GET_ALL)) {
			htmlTable.append("<table border='1'>");
			htmlTable.append("<tr><th>Product name</th><th>Id</th><th>Characteristics</th><th>Category name</th></tr>"); // Заголовки таблиці
			while (rs.next()) {
				htmlTable.append("<tr>");
				htmlTable.append("<td>").append(rs.getString("product_name")).append("</td>");
				htmlTable.append("<td>").append(rs.getString("id_product")).append("</td>");
				htmlTable.append("<td>").append(rs.getString("characteristics")).append("</td>");
				htmlTable.append("<td>").append(rs.getString("category_name")).append("</td>");
				htmlTable.append("</tr>");
			}
			htmlTable.append("</table>");

		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao showNumbers SQL exception", e);
			throw new ServerException(e);
		}
		return htmlTable;
	}

	@Override
	public Optional<Dish> getById(Long id) {
		Optional<Dish> dish = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return dish;
	}

	@Override
	public void create(Dish dish) {
		try (PreparedStatement query = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, dish.getName());
			query.setString(2, dish.getDescription());
			query.setDouble(3, dish.getWeight());
			query.setBigDecimal(4, dish.getCost());
			query.setLong(5, dish.getCategory().getId());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				dish.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao create SQL exception", e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Dish dish) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
			query.setString(1, dish.getName());
			query.setString(2, dish.getDescription());
			query.setDouble(3, dish.getWeight());
			query.setBigDecimal(4, dish.getCost());
			query.setLong(5, dish.getCategory().getId());
			query.setLong(6, dish.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao update SQL exception: " + dish.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}

	}

	@Override
	public StringBuilder searchProductsByName(String name) {
		StringBuilder htmlTable = new StringBuilder();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_PRODUCT_BY_NAME)) {
			query.setString(1, name);
			ResultSet resultSet = query.executeQuery();

			htmlTable.append("<table border='1'>");
			htmlTable.append("<tr><th>Product name</th><th>Id</th><th>Characteristics</th><th>Category name</th></tr>"); // Заголовки таблиці
			while (resultSet.next()) {
				htmlTable.append("<tr>");
				htmlTable.append("<td>").append(resultSet.getString("product_name")).append("</td>");
				htmlTable.append("<td>").append(resultSet.getString("id_product")).append("</td>");
				htmlTable.append("<td>").append(resultSet.getString("characteristics")).append("</td>");
				htmlTable.append("<td>").append(resultSet.getString("category_name")).append("</td>");
				htmlTable.append("</tr>");
			}
			htmlTable.append("</table>");
		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao searchDishByName SQL exception: " + name, e);
			throw new ServerException(e);
		}
		return htmlTable;
	}

	@Override
	public StringBuilder searchDishByCategoryName(String categoryName) {
		StringBuilder htmlTable = new StringBuilder();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_PRODUCT_BY_CATEGORY_NAME)) {
			query.setString(1, categoryName);
			ResultSet resultSet = query.executeQuery();

			htmlTable.append("<table border='1'>");
			htmlTable.append("<tr><th>Product name</th><th>Id</th><th>Characteristics</th><th>Category name</th></tr>"); // Заголовки таблиці
			while (resultSet.next()) {
				htmlTable.append("<tr>");
				htmlTable.append("<td>").append(resultSet.getString("product_name")).append("</td>");
				htmlTable.append("<td>").append(resultSet.getString("id_product")).append("</td>");
				htmlTable.append("<td>").append(resultSet.getString("characteristics")).append("</td>");
				htmlTable.append("<td>").append(resultSet.getString("category_name")).append("</td>");
				htmlTable.append("</tr>");
			}
			htmlTable.append("</table>");
		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao searchDishByName SQL exception: " + categoryName, e);
			throw new ServerException(e);
		}
		return htmlTable;
	}

	@Override
	public List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate) {
		List<Dish> dishes = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_MOST_POPULAR_DISHES_IN_PERIOD)) {
			query.setDate(1, Date.valueOf(fromDate));
			query.setDate(2, Date.valueOf(toDate));
			query.setDate(3, Date.valueOf(fromDate));
			query.setDate(4, Date.valueOf(toDate));
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcProductDao searchMostPopularDishesInPeriod SQL exception", e);
			throw new ServerException(e);
		}
		return dishes;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcCategoryDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}


}
