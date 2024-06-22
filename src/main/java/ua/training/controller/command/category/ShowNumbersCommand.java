package ua.training.controller.command.category;

import ua.training.constants.Attribute;
import ua.training.constants.Page;
import ua.training.controller.command.Command;
import ua.training.entity.Category;
import ua.training.service.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ShowNumbersCommand implements Command {

    private final CategoryService categoryService;

    public ShowNumbersCommand(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder categories = categoryService.showNumbers();
        return categories.toString();
    }
}

