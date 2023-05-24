package com.example.demolab3.controller;

import com.example.demolab3.entity.Book;
import com.example.demolab3.entity.Category;
import com.example.demolab3.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategory(Model model, @Valid @ModelAttribute("category") Category category, BindingResult result) {
        if (result.hasErrors()) {
//            model.addAttribute("category", new Category());
            return "category/add";
        }

        categoryService.addCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "category/edit";
    }

    @PostMapping("/edit")
    public String editBCategory(Model model, @Valid @ModelAttribute("category") Category updateCategory, BindingResult result) {
        if (result.hasErrors()) {
            return "category/edit";
        }

        categoryService.updateCategory(updateCategory);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}
