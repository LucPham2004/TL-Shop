package com.e_shop.Shoe_Shop.Entity.product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.e_shop.Shoe_Shop.Entity.brand.Brand;
import com.e_shop.Shoe_Shop.Entity.brand.BrandRepository;
import com.e_shop.Shoe_Shop.Entity.category.CategoryRepository;
import com.e_shop.Shoe_Shop.Entity.category.Category;
import com.e_shop.Shoe_Shop.Entity.product.detail.ProductDetail;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Convert Product and ProductDetail to DTO
    public ProductDTO convertToDTO(Product product) {
        Set<ProductDTO.ProductDetailDTO> detailDTOs = product.getDetails().stream()
            .map(this::convertDetailToDTO)
            .collect(Collectors.toSet());

        return new ProductDTO(
            product.getId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductImage(),
            product.getProductPrice(),
            product.getProductQuantity(),
            product.getDiscountPercent(),
            product.getReviewCount(),
            product.getAverageRating(),
            product.getBrand().getName(),
            product.getCategory().stream().map(Category::getName).collect(Collectors.toSet()),
            detailDTOs
        );
    }

    public ProductDTO.ProductDetailDTO convertDetailToDTO(ProductDetail productDetail) {
        return new ProductDTO.ProductDetailDTO(
            productDetail.getId(),
            productDetail.getColor(),
            productDetail.getSize(),
            productDetail.getQuantity()
        );
    }

    // Convert DTO to Product and ProductDetail Entity
    public Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductName(productDTO.getProductName());
        product.setProductDescription(productDTO.getProductDescription());
        product.setProductImage(productDTO.getProductImage());
        product.setProductPrice(productDTO.getProductPrice());
        product.setProductQuantity(productDTO.getProductQuantity());
        product.setDiscountPercent(productDTO.getDiscountPercent());
        product.setReviewCount(productDTO.getReviewCount());
        product.setAverageRating(productDTO.getAverageRating());
        
        Brand brand = brandRepository.findByName(productDTO.getBrandName());
        if (brand != null) {
            product.setBrand(brand);
        }

        Set<Category> categories = productDTO.getCategories().stream()
                .map(categoryId -> categoryRepository.findByName(categoryId))
                .collect(Collectors.toSet());
        product.setCategory(categories);

        Set<ProductDetail> details = productDTO.getDetails().stream()
            .map(this::convertDetailToEntity)
            .collect(Collectors.toSet());
        product.setDetails(details);

        return product;
    }

    public ProductDetail convertDetailToEntity(ProductDTO.ProductDetailDTO productDetailDTO) {
        ProductDetail detail = new ProductDetail();
        detail.setId(productDetailDTO.getId());
        detail.setColor(productDetailDTO.getColor());
        detail.setSize(productDetailDTO.getSize());
        detail.setQuantity(productDetailDTO.getQuantity());
        return detail;
    }

    // CRUD Methods
    // GET
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    public List<ProductDTO> getProductsByBrand(String brandName) {
        boolean exists = brandRepository.existsByName(brandName);
        if (!exists) {
            throw new IllegalStateException("Brand with name: " + brandName + " doesn't exist!");
        }
        List<Product> products = productRepository.findByBrandName(brandName);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String categoryName) {
        boolean exists = categoryRepository.existsByName(categoryName);
        if (!exists) {
            throw new IllegalStateException("Category with name: " + categoryName + " doesn't exist!");
        }
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // POST
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        if(productRepository.existsByProductNameAndProductDescription(product.getProductName(), product.getProductDescription()))
            throw new IllegalStateException("Product with name: " + product.getProductName() + 
            " and description: " + product.getProductDescription() + " already exists!");
        else{
            product.getDetails().forEach(detail -> detail.setProduct(product));

            Product savedProduct = productRepository.save(product);
            return convertToDTO(savedProduct);
        }
    }

    public String uploadImages(String productName, MultipartFile[] files) {
        String uploadDir =  "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/Shoe_Shop/src/main/resources/static/img/products/" + productName;

        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        Path path = Paths.get(uploadDir);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to create upload directory");
        }

        for (MultipartFile file : files) {
            @SuppressWarnings("null")
            String filePath = uploadDir + "/" + StringUtils.cleanPath(file.getOriginalFilename());
            try {
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to upload images");
            }
        }
        return "Upload Images succesfully";
    }

    // DELETE
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    // PUT
    @Transactional
    public void updateProduct(ProductDTO productDTO) {
        Product productToUpdate = productRepository.findById(productDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Update product fields
        productToUpdate.setProductName(productDTO.getProductName());
        productToUpdate.setProductDescription(productDTO.getProductDescription());
        productToUpdate.setProductImage(productDTO.getProductImage());
        productToUpdate.setProductPrice(productDTO.getProductPrice());
        productToUpdate.setProductQuantity(productDTO.getProductQuantity());
        productToUpdate.setDiscountPercent(productDTO.getDiscountPercent());
        productToUpdate.setReviewCount(productDTO.getReviewCount());
        productToUpdate.setAverageRating(productDTO.getAverageRating());

        // Update brand
        Brand brand = brandRepository.findByName(productDTO.getBrandName());
        if (brand != null) {
            productToUpdate.setBrand(brand);
        }

        // Update categories
        Set<Category> categories = productDTO.getCategories().stream()
            .map(categoryId -> categoryRepository.findByName(categoryId))
            .collect(Collectors.toSet());
        productToUpdate.setCategory(categories);

        // Update product details
        Set<ProductDetail> updatedDetails = productDTO.getDetails().stream()
            .map(this::convertDetailToEntity)
            .collect(Collectors.toSet());
        productToUpdate.getDetails().clear();
        productToUpdate.getDetails().addAll(updatedDetails);
        updatedDetails.forEach(detail -> detail.setProduct(productToUpdate));

        productRepository.save(productToUpdate);
    }
}
