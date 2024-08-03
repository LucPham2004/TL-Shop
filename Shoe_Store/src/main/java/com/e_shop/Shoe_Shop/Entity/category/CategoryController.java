package com.e_shop.Shoe_Shop.Entity.category;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(path = "api/v1/category")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping(path = "/{id}")
    public Category getCategoryById(@PathVariable Integer id)
    {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public Category addNewCategory(@RequestBody String categoryName) {
        return categoryService.addNewCategory(categoryName);
    }
    
    @DeleteMapping(path = "/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
    }
}
