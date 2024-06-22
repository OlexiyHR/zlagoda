package ua.training.dao;

import ua.training.entity.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao extends GenericDao<Category, Long>, AutoCloseable {
	
	List<Category> searchCategoriesByName(String categoryName);
	
	void close();

	StringBuilder showNumbers() throws ClassNotFoundException, SQLException;
}
