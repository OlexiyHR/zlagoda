package ua.training.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ua.training.converter.DishDtoDishConverter;
import ua.training.dao.DaoFactory;
import ua.training.dao.ProductDao;
import ua.training.dto.DishDto;
import ua.training.entity.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductService.class);
    static final String GET_ALL_DISHES = "Get all dishes";
    static final String GET_DISH_BY_ID = "Get dish by id: %d";
    static final String CREATE_DISH = "Create dish: %s";
    static final String UPDATE_DISH = "Update dish: %d";
    static final String DELETE_DISH = "Delete dish: %d";
    static final String SEARCH_PRODUCTS_BY_NAME = "Search products by name: %s";
    static final String SEARCH_PRODUCTS_BY_CATEGORY_NAME = "Search products by category name: %s";
    static final String SEARCH_MOST_POPULAR_DISH_PER_PERIOD = "Search most popular dish per period from %s to %s";

    private final DaoFactory daoFactory;

    ProductService(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private static class Holder {
        static final ProductService INSTANCE = new ProductService(DaoFactory.getDaoFactory());
    }

    public static ProductService getInstance() {
        return Holder.INSTANCE;
    }

    public StringBuilder getAllProducts() {
        LOGGER.info(GET_ALL_DISHES);
        try (ProductDao productDao = daoFactory.createProductDao()) {
            return productDao.getAll();
        }
    }

    public Optional<Dish> getDishById(Long dishId) {
        LOGGER.info(String.format(GET_DISH_BY_ID, dishId));
        try (ProductDao dishDao = daoFactory.createProductDao()) {
            return dishDao.getById(dishId);
        }
    }

    public void createDish(DishDto dishDto) {
        LOGGER.info(String.format(CREATE_DISH, dishDto.getName()));
        Dish dish = DishDtoDishConverter.toDish(dishDto);
        try (ProductDao dishDao = daoFactory.createProductDao()) {
            dishDao.create(dish);
        }
    }

    public void updateDish(DishDto dishDto) {
        LOGGER.info(String.format(UPDATE_DISH, dishDto.getId()));
        Dish dish = DishDtoDishConverter.toDish(dishDto);
        try (ProductDao dishDao = daoFactory.createProductDao()) {
            dishDao.update(dish);
        }
    }

    public void deleteDish(Long dishId) {
        LOGGER.info(String.format(DELETE_DISH, dishId));
        try (ProductDao dishDao = daoFactory.createProductDao()) {
            dishDao.delete(dishId);
        }
    }

    public StringBuilder searchProductsByName(String productName) {
        LOGGER.info(String.format(SEARCH_PRODUCTS_BY_NAME, productName));
        try (ProductDao productsDao = daoFactory.createProductDao()) {
            return productsDao.searchProductsByName(productName);
        }
    }

    public StringBuilder searchDishesByCategoryName(String categoryName) {
        LOGGER.info(String.format(SEARCH_PRODUCTS_BY_CATEGORY_NAME, categoryName));
        try (ProductDao productsDao = daoFactory.createProductDao()) {
            return productsDao.searchDishByCategoryName(categoryName);
        }
    }

    public List<Dish> searchMostPopularDishesPerPeriod(LocalDate fromDate, LocalDate toDate) {
        LOGGER.info(String.format(SEARCH_MOST_POPULAR_DISH_PER_PERIOD, fromDate.toString(),
                toDate.toString()));
        try (ProductDao dishDao = daoFactory.createProductDao()) {
            return dishDao.searchMostPopularDishesPerPeriod(fromDate, toDate);
        }
    }
}
