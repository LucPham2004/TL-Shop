package com.e_shop.Shoe_Shop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.e_shop.Shoe_Shop.dto.dto.ProductDTO;
import com.e_shop.Shoe_Shop.dto.dto.ProductFullInfo;
import com.e_shop.Shoe_Shop.dto.dto.ProductWithDetails;
import com.e_shop.Shoe_Shop.dto.request.AddProductImages;
import com.e_shop.Shoe_Shop.dto.request.ProductEditRequest;
import com.e_shop.Shoe_Shop.service.ProductService;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/fullInfo")
    public List<ProductFullInfo> getAllProductsFullInfo() {
        return productService.getAllProductsFullInfo();
    }

    @GetMapping("/withdetails")
    public List<ProductWithDetails> getAllProductsWithDetails() {
        return productService.getAllProductsWithDetails();
    }

    @GetMapping("/{id}")
    public ProductWithDetails getProduct(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/topproducts")
    public List<ProductDTO> getTopProducts() {
        return productService.getTopProducts();
    }

    @GetMapping("/low-remaining")
    public List<ProductDTO> getLowRemainingProducts() {
        return productService.lowRemainingProducts();
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam("keyword") String keywword) {
        return productService.searchProducts(keywword);
    }

    @GetMapping(path = "/brand/{brandName}")
    public List<ProductDTO> getProductsByBrand(@PathVariable String brandName) {
        return productService.getProductsByBrand(brandName);
    }

    @GetMapping(path = "/category/{categoryName}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String categoryName) {
        return productService.getProductsByCategory(categoryName);
    }

    // POST
    @PostMapping
    public ProductWithDetails createProduct(@RequestBody ProductWithDetails productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PostMapping("/addMany")
    public List<ProductWithDetails> createManyProducts(@RequestBody List<ProductWithDetails> productDTO) {
        for(ProductWithDetails newProductDTO :productDTO) {
            productService.saveProduct(newProductDTO);
        }
        return null;
    }

    @PostMapping(path = "/image/upload")
    public ProductFullInfo uploadImages(@RequestBody AddProductImages request){
        return productService.uploadImages(request);
    }

    // PUT
    @PutMapping("/{id}")
    public ProductWithDetails updateProduct(@PathVariable Integer id, @RequestBody ProductWithDetails productDTO) {
        productDTO.setId(id);
        return productService.saveProduct(productDTO);
    }

    @PutMapping("/edit")
    public ProductWithDetails editProductMainInfo(@RequestBody ProductEditRequest request) {
        return productService.editProductMainInfo(request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}
