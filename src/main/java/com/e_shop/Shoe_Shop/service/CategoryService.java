package com.e_shop.Shoe_Shop.service;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.entity.Category;
import com.e_shop.Shoe_Shop.exception.AppException;
import com.e_shop.Shoe_Shop.exception.ErrorCode;
import com.e_shop.Shoe_Shop.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // GET
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Integer id) {
        boolean exists = categoryRepository.existsById(id);
        if(!exists)
        {
            throw new AppException(ErrorCode.ENTITY_NOT_EXISTED);
        }
        return categoryRepository.findById(id).get();
    }

    // POST
    public Category addNewCategory(String categoryName)
    {
        boolean exists = categoryRepository.existsByName(categoryName);
        if(exists)
        {
            throw new AppException(ErrorCode.ENTITY_EXISTED);
        }
        Category category = new Category();
        category.setName(categoryName);
        return categoryRepository.save(category);
    }

    // DELETE
    public void deleteCategory(Integer id) {
        boolean exists = categoryRepository.existsById(id);
        if(!exists)
        {
            throw new AppException(ErrorCode.ENTITY_NOT_EXISTED);
        }
        categoryRepository.deleteById(id);
    }
}
