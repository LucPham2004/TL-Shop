package com.e_shop.Shoe_Shop.Entity.brand;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public Iterable<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }
    
    @GetMapping(path = "/{id}")
    public Brand getBrandById(@PathVariable Integer id) {
        return brandService.getBrandById(id);
    }

    @PostMapping
    public Brand createBrand(@RequestBody Brand brand) {
        return brandService.addBrand(brand);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrand(id);
    }
}
