package com.e_shop.Shoe_Shop.Entity.product;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.e_shop.Shoe_Shop.DTO.dto.ProductDTO;

import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin(maxAge = 3600)
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

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Integer id) {
        return productService.getProductById(id);
    }

    @GetMapping("/topproducts")
    public List<ProductDTO> getTopProducts() {
        return productService.getTopProducts();
    }

    @GetMapping("/search")
    public List<ProductDTO> searchProducts(@RequestParam("keyword") String keywword) {
        return productService.searchProducts(keywword);
    }

    @GetMapping("/images/{productName}")
    public ResponseEntity<byte[]> getProductImages(@PathVariable String productName) {
        return productService.getProductImages(productName);
    }

    @GetMapping(path = "/brand/{brandName}")
    public List<ProductDTO> getProductsByBrand(@PathVariable String brandName)
    {
        return productService.getProductsByBrand(brandName);
    }

    @GetMapping(path = "/category/{categoryName}")
    public List<ProductDTO> getProductsByCategory(@PathVariable String categoryName)
    {
        return productService.getProductsByCategory(categoryName);
    }

    // POST
    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @PostMapping(path = "/uploadImages", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImages(@RequestParam("productName") String productName, 
                            @RequestParam("productImages") MultipartFile[] productImages){
        return productService.uploadImages(productName, productImages);
    }

    // PUT
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id);
        return productService.saveProduct(productDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
    }
}
