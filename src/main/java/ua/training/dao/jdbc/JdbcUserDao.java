package ua.training.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.dao.UserDao;
import ua.training.entity.Role;
import ua.training.entity.User;
import ua.training.exception.ServerException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserDao implements UserDao {

	private static final Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);

	private static String GET_ALL = "SELECT * FROM `user` ORDER BY surname";
	private static String GET_BY_ID = "SELECT * FROM `user` WHERE id_user=?";
	private static String GET_BY_CREDENTIALS = "SELECT * FROM `employee` WHERE email=? AND password=?";
	private static String CREATE = "INSERT INTO `user`"
			+ " (name, surname, address, phone, role, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static String UPDATE = "UPDATE `user`"
			+ " SET name=?, surname=?, address=?, phone=?, role=?, email=?, password=?" + " WHERE id_user=? ";
	private static String DELETE = "DELETE FROM `user` WHERE id_user=?";
	private static String SEARCH_USERS_BY_SURNAME = "SELECT * FROM `user` WHERE LOWER(surname) LIKE CONCAT('%', LOWER(?), '%')";
	private static String SEARCH_USERS_BY_ROLE = "SELECT * FROM `user` WHERE role=?";
	private static String SEARCH_BEST_WAITERS_PER_PERIOD = "SELECT * FROM `user` WHERE id_user IN "
																	+ "(SELECT id_user FROM `order`"
																	+ " WHERE `date` BETWEEN ? AND ?" 
																	+ " GROUP BY id_user"
																	+ " HAVING COUNT(id_order)=(SELECT MAX(orders_number)" 
																							+ " FROM (SELECT COUNT(id_order) AS orders_number"
																							+ " FROM `order` WHERE `date` BETWEEN ? AND ?"
																							+ " GROUP BY id_user) AS `orders_counter`))";

	// table columns names
	private static String ID = "id_employee";
	private static String SURNAME = "empl_surname";
	private static String NAME = "empl_name";
	private static String PATHRONYMIC = "empl_pathronymic";
	private static String ROLE = "empl_role";
	private static String SALARY = "salary";
	private static String BIRTH = "date_of_birth";
	private static String START = "date_of_start";
	private static String PHONE = "phone_number";
	private static String CITY = "city";
	private static String STREET = "street";
	private static String ZIP = "zip_code";
	private static String EMAIL = "email";
	private static String PASSWORD = "password";

	private Connection connection;
	private boolean connectionShouldBeClosed;

	public JdbcUserDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcUserDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<User> getAll() {
		List<User> users = new ArrayList<>();

		try (Statement query = connection.createStatement(); ResultSet resultSet = query.executeQuery(GET_ALL)) {
			while (resultSet.next()) {
				users.add(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao getAll SQL exception", e);
			throw new ServerException(e);
		}
		return users;
	}

	@Override
	public Optional<User> getById(Long id) {
		Optional<User> user = Optional.empty();
		System.out.println(connection);
		try (PreparedStatement query = connection.prepareStatement(GET_BY_ID)) {
			query.setLong(1, id);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao getById SQL exception: " + id, e);
			throw new ServerException(e);
		}
		return user;
	}

		@Override
	public Optional<User> getUserByCredentials(String email, String password) {
		Optional<User> user = Optional.empty();
		try (PreparedStatement query = connection.prepareStatement(GET_BY_CREDENTIALS)) {
			query.setString(1, email);
			query.setString(2, password);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				user = Optional.of(extractUserFromResultSet(resultSet));
			}

		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao getUserByCredentials SQL exception: " + email, e);
			throw new ServerException(e);
		}
		return user;
	}

	@Override
	public void create(User user) {
		try (PreparedStatement query = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
			query.setString(1, user.getName());
			query.setString(2, user.getSurname());
			query.setString(4, user.getPhone());
			query.setString(5, user.getRole().getValue());
			query.setString(6, user.getEmail());
			query.setString(7, user.getPassword());
			query.executeUpdate();

			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				user.setId(keys.getLong(1));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao create SQL exception", e);
			throw new ServerException(e);
		}
	}

	@Override
	public void update(User user) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE)) {
			query.setString(1, user.getName());
			query.setString(2, user.getSurname());
			query.setString(4, user.getPhone());
			query.setString(5, user.getRole().getValue());
			query.setString(6, user.getEmail());
			query.setString(7, user.getPassword());
			query.setLong(8, user.getId());
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao update SQL exception: " + user.getId(), e);
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(Long id) {
		try (PreparedStatement query = connection.prepareStatement(DELETE)) {
			query.setLong(1, id);
			query.executeUpdate();
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao delete SQL exception: " + id, e);
			throw new ServerException(e);
		}
	}

	@Override
	public List<User> searchUsersBySurname(String surname) {
		List<User> users = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_USERS_BY_SURNAME)) {
			query.setString(1, surname);
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				users.add(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao searchUsersBySurname SQL exception: " + surname, e);
			throw new ServerException(e);
		}
		return users;
	}

	@Override
	public List<User> searchUsersByRole(Role role) {
		List<User> users = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_USERS_BY_ROLE)) {
			query.setString(1, role.getValue());
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				users.add(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao searchUsersByRole SQL exception: " + role.getValue(), e);
			throw new ServerException(e);
		}
		return users;
	}

	@Override
	public List<User> searchBestWaitersPerPeriod(LocalDate fromDate, LocalDate toDate) {
		List<User> users = new ArrayList<>();

		try (PreparedStatement query = connection.prepareStatement(SEARCH_BEST_WAITERS_PER_PERIOD)) {
			query.setDate(1, Date.valueOf(fromDate));
			query.setDate(2, Date.valueOf(toDate));
			query.setDate(3, Date.valueOf(fromDate));
			query.setDate(4, Date.valueOf(toDate));
			ResultSet resultSet = query.executeQuery();
			while (resultSet.next()) {
				users.add(extractUserFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			LOGGER.error("JdbcUserDao searchBestWaitersPerPeriod SQL exception", e);
			throw new ServerException(e);
		}
		return users;
	}

	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao Connection can't be closed", e);
				throw new ServerException(e);
			}
		}
	}

	protected static User extractUserFromResultSet(ResultSet resultSet) throws SQLException {

		return new User.Builder().setId(resultSet.getLong(ID))
				.setName(resultSet.getString(NAME))
				.setSurname(resultSet.getString(SURNAME))
				.setPathronymic(resultSet.getString(PATHRONYMIC))
				.setSalary(resultSet.getBigDecimal(SALARY))
				.setBirth(resultSet.getDate(BIRTH))
				.setStart(resultSet.getDate(START))
				.setPhone(resultSet.getString(PHONE))
				.setRole(Role.forValue(resultSet.getString(ROLE)))
				.setCity(resultSet.getString(CITY))
				.setStreet(resultSet.getString(STREET))
				.setZip(resultSet.getString(ZIP))
				.setEmail(resultSet.getString(EMAIL))
				.setPassword(resultSet.getString(PASSWORD))
				.build();
	}
	
	protected static User extractUserGeneralInfoFromResultSet(ResultSet resultSet) throws SQLException {

		return new User.Builder().setId(resultSet.getLong(ID)).setName(resultSet.getString(NAME))
				.setSurname(resultSet.getString(SURNAME)).build();
	}

	
}
