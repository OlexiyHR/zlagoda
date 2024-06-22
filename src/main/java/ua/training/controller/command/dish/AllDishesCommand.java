package ua.training.controller.command.dish;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Category;
import ua.training.entity.Dish;
import ua.training.service.CategoryService;
import ua.training.service.DishService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AllDishesCommand implements Command{
	
	private final DishService dishService;
	private final CategoryService categoryService;

	public AllDishesCommand(DishService dishService, CategoryService categoryService) {
		this.dishService = dishService;
		this.categoryService = categoryService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Dish> dishes = dishService.getAllDishes();
		List<Category> categories = categoryService.getAllCategories();
		
		request.setAttribute(Attribute.DISHES, dishes);
		request.setAttribute(Attribute.CATEGORIES, categories);
		return Page.ALL_DISHES_VIEW;
	}
}
