package ua.training.dao.jdbc;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.dao.*;
import ua.training.exception.ServerException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that represent dao factory that produces DAOs for JDBC persistent
 * storage access implementation and use DB connection pool for getting
 * connections to db
 * 
 * @author Solomka
 *
 */
public class JdbcDaoFactory extends DaoFactory {

	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoFactory.class);

	private Connection connection;

	/**
	 * Get DataSource implementation from Initial Context by means of JNDI mechanism
	 */
	public JdbcDaoFactory() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "ol12345");
		} catch (Exception e) {
			LOGGER.error("Can't load pool connection from Initial Context", e);
			throw new ServerException(e);
		}
	}

	/**
	 * Get custom Connection wrapper for providing transaction execution
	 * 
	 * @return a connection to the data source
	 * @exception ServerException if a database access error occurs
	 */
	@Override
	public DaoConnection getConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "ol12345");
			return new JdbcDaoConnection(connection);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDao createUserDao() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket", "root", "ol12345");
			return new JdbcUserDao(connection, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserDao createUserDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcUserDao(sqlConnection);
	}

	@Override
	public CategoryDao createCategoryDao() {
		try {
			return new JdbcCategoryDao(connection, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public CategoryDao createCategoryDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcCategoryDao(sqlConnection);
	}
	
	@Override
	public DishDao createDishDao() {
		try {
			return new JdbcDishDao(connection, true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DishDao createDishDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcDishDao(sqlConnection);
	}
	
	@Override
	public OrderDao createOrderDao() {
		try {
			return new JdbcOrderDao(connection, true);
		} catch (Exception e) {
		throw new RuntimeException(e);
	}
	}

	@Override
	public OrderDao createOrderDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcOrderDao(sqlConnection);
	}
}
