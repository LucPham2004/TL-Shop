package com.e_shop.Shoe_Shop.Entity.category;

import org.springframework.stereotype.Service;

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
            throw new IllegalStateException("Category with id: " + id +" doesn't exists!");
        }
        return categoryRepository.findById(id).get();
    }

    // POST
    public void addNewCategory(Category category)
    {
        boolean exists = categoryRepository.existsById(category.getId());
        if(exists)
        {
            throw new IllegalStateException("Category with id: " + category.getId() +" already exists!");
        }
        categoryRepository.save(category);
    }

    // DELETE
    public void deleteCategory(Integer id) {
        boolean exists = categoryRepository.existsById(id);
        if(!exists)
        {
            throw new IllegalStateException("Category with id: " + id +" doesn't exists!");
        }
        categoryRepository.deleteById(id);
    }
}
