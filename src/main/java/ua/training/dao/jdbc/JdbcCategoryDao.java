package ua.training.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.dao.CategoryDao;
import ua.training.entity.Category;
import ua.training.exception.ServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcCategoryDao implements CategoryDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcCategoryDao.class);

	private static String ShowNumbers = "SELECT category.category_number, category_name, COUNT(customer_card.card_number) AS count_of_customers " +
			                            "FROM ((((category LEFT JOIN product ON category.category_number = product.category_number)" +
	                                          "LEFT JOIN store_Product ON product.id_product = store_product.id_product)" +
			                     "            LEFT JOIN sale ON store_product.UPC = sale.UPC) " +
			                     "            LEFT JOIN bcheck ON sale.check_number = bcheck.check_number) " +
			                     "            LEFT JOIN customer_card ON bcheck.card_number = customer_card.card_number " +
			                             "GROUP BY category.category_number, category_name;";

	private static String GET_ALL = "SELECT * FROM `category` ORDER BY name";
	private static String GET_BY_ID = "SELECT * FROM `category` WHERE id_category=?";
	private static String CREATE = "INSERT INTO `category` (name) VALUES (?)";
	private static String UPDATE = "UPDATE `category` SET name=? WHERE id_category=?";
	private static String DELETE = "DELETE FROM `category` WHERE id_category=?";
	private static String SEARCH_CATEGORY_BY_NAME = "SELECT * FROM `category` WHERE LOWER(name) LIKE CONCAT('%', LOWER(?), '%')";

	// table columns names
	private static String ID = "category_number";
	private static String NAME = "category_name";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcCategoryDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcCategoryDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	public StringBuilder showNumbers() throws ClassNotFoundException, SQLException {
		StringBuilder htmlTable = new StringBuilder();
		try (Statement query = connection.createStatement();

			 ResultSet rs = query.executeQuery(ShowNumbers)) {
			htmlTable.append("<table border='1'>");
			htmlTable.append("<tr><th>category_number</th><th>category_name</th><th>count_of_customers</th></tr>"); // Заголовки таблиці
			while (rs.next()) {
				htmlTable.append("<tr>");
				htmlTable.append("<td>").append(rs.getString("category_number")).append("</td>");
				htmlTable.append("<td>").append(rs.getString("category_name")).append("</td>");
				htmlTable.append("<td>").append(rs.getString("count_of_customers")).append("</td>");
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
	public StringBuilder getAll() {
		StringBuilder htmlTable = new StringBuilder();

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
			while (resultSet.next()) {
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return htmlTable;
	}

	@Override
	public Optional<Category> getById(Long id) {
		Optional<Category> category = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				category = Optional.of(extractCategoryFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return category;
	}

	@Override
	public void create(Category category) {
		try (PreparedStatement query = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, category.getName());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				category.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao create SQL exception", e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(Category category) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
			query.setString(1, category.getName());
			query.setLong(2, category.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao update SQL exception: " + category.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcCategoryDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public List<Category> searchCategoriesByName(String categoryName) {
		List<Category> categories = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_CATEGORY_BY_NAME)) {
			query.setString(1, categoryName);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				categories.add(extractCategoryFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao searchCategoryByName SQL exception: " + categoryName, e);
			throw new ServerException(e);
		}
		return categories;
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

	protected static Category extractCategoryFromResultSet(ResultSet resultSet) throws SQLException {
		return new Category.Builder().setId(resultSet.getLong(ID)).setName(resultSet.getString(NAME)).build();
	}


}
