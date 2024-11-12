package com.e_shop.Shoe_Shop.service;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.entity.Brand;
import com.e_shop.Shoe_Shop.repository.BrandRepository;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    // GET
    public Iterable<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(Integer id)
    {
        boolean exists = brandRepository.existsById(id);
        if(!exists)
        {
            throw new IllegalStateException("Brand with id: " + id +" doesn't exists!");
        }
        return brandRepository.findById(id).get();
    }

    // POST
    public Brand addBrand(String brandName) {
        boolean exists = brandRepository.existsByName(brandName);
        if(exists)
        {
            throw new IllegalStateException("Brand with name: " + brandName +" already exists!");
        }
        Brand brand = new Brand();
        brand.setName(brandName);
        return brandRepository.save(brand);
    }

    // DELETE
    public void deleteBrand(Integer id) {
        boolean exists = brandRepository.existsById(id);
        if(!exists)
        {
            throw new IllegalStateException("Brand with id: " + id +" doesn't exists!");
        }
        brandRepository.deleteById(id);
    }
}
