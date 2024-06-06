package com.e_shop.Shoe_Shop.Entity.brand;

import org.springframework.stereotype.Service;

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
    public Brand addBrand(Brand brand) {
        boolean exists = brandRepository.existsById(brand.getId());
        if(exists)
        {
            throw new IllegalStateException("Brand with id: " + brand.getId() +" already exists!");
        }
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
