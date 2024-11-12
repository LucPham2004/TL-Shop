package com.e_shop.Shoe_Shop.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.e_shop.Shoe_Shop.dto.dto.ProductDTO;
import com.e_shop.Shoe_Shop.dto.dto.ProductInfoDTO;
import com.e_shop.Shoe_Shop.entity.Brand;
import com.e_shop.Shoe_Shop.entity.Category;
import com.e_shop.Shoe_Shop.entity.Product;
import com.e_shop.Shoe_Shop.entity.ProductDetail;
import com.e_shop.Shoe_Shop.repository.BrandRepository;
import com.e_shop.Shoe_Shop.repository.CategoryRepository;
import com.e_shop.Shoe_Shop.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    
    public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
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
            product.getProductQuantitySold(),
            product.getProductDayCreated(),
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
            productDetail.getQuantity(),
            productDetail.getQuantitySold()
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
        product.setProductQuantitySold(productDTO.getProductQuantitySold());
        product.setProductDayCreated(new Date());
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
        detail.setQuantitySold(productDetailDTO.getQuantitySold());
        return detail;
    }

    public ProductInfoDTO convertToInfoDTO(Product product) {
        return new ProductInfoDTO(
            product.getId(),
            product.getProductName(),
            product.getProductDescription(),
            product.getProductImage(),
            product.getProductPrice(),
            product.getDiscountPercent(),
            product.getBrand().getName(),
            product.getCategory().stream().map(Category::getName).collect(Collectors.toSet())
        );
    }

    // CRUD Methods
    // GET
    public List<ProductInfoDTO> getAllProducts() {
        return productRepository.findAll().stream()
        .map(this::convertToInfoDTO)
        .collect(Collectors.toList());
    }

    public List<ProductDTO> getAllProductsWithDetails() {
        return productRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Integer id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDTO(product);
    }

    public List<ProductInfoDTO> getProductsByBrand(String brandName) {
        boolean exists = brandRepository.existsByName(brandName);
        if (!exists) {
            throw new IllegalStateException("Brand with name: " + brandName + " doesn't exist!");
        }
        List<Product> products = productRepository.findByBrandName(brandName);
        return products.stream()
                .map(this::convertToInfoDTO)
                .collect(Collectors.toList());
    }

    public List<ProductInfoDTO> getProductsByCategory(String categoryName) {
        boolean exists = categoryRepository.existsByName(categoryName);
        if (!exists) {
            throw new IllegalStateException("Category with name: " + categoryName + " doesn't exist!");
        }
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(this::convertToInfoDTO)
                .collect(Collectors.toList());
    }

    // Return top-seller, on-sale products
    public List<ProductInfoDTO> getTopProducts() {
        List<Product> allproducts = productRepository.findAll();
        List<Product> resultList = new ArrayList<>();
        int index = 0;

        allproducts.sort(Comparator.comparingInt(product -> product.getProductQuantitySold()));
        for(Product productDTO: allproducts) {
            if(index >= 12)
                break;
            resultList.add(productDTO);
            index++;
        }

        index = 0;
        allproducts.sort(Comparator.comparingDouble(product -> product.getDiscountPercent()));
        for(Product productDTO: allproducts) {
            if(index >= 12)
                break;
            resultList.add(productDTO);
            index++;
        }
        
        return resultList.stream().map(this::convertToInfoDTO).collect(Collectors.toList());
    }

    // Search products
    public List<ProductInfoDTO> searchProducts(String keywword) {
        return productRepository.findByProductNameContainingOrProductDescriptionContainingOrCategoryNameOrBrandNameContaining(
            keywword, keywword, keywword, keywword).stream()
        .map(this::convertToInfoDTO)
        .collect(Collectors.toList());
    }

    public List<ProductInfoDTO> searchProductsByIdContaining(int id) {
        return productRepository.findByIdContaining(id).stream()
        .map(this::convertToInfoDTO)
        .collect(Collectors.toList());
    }

    public List<ProductInfoDTO> lowRemainingProducts() {
        List<Product> products = productRepository.findAll();

        List<Product> lowRemainingProducts = new ArrayList<>();

        for(Product productDTO: products) {
            Set<ProductDetail> productDetailsList = productDTO.getDetails();
            for(ProductDetail productDetailsDTO: productDetailsList) {
                if (productDetailsDTO.getQuantity() < 20) {
                    lowRemainingProducts.add(productDTO);
                    break;
                }
            }
        }

        return lowRemainingProducts.stream()
        .map(this::convertToInfoDTO)
        .collect(Collectors.toList());
    }

    // Get product Images
    public ResponseEntity<byte[]> getProductImages(String productName) {
        try {
            String uploadDir = "temporaryDisabled/img/products/";
            Path path = Path.of(uploadDir + productName);

            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            byte[] imageBytes = Files.readAllBytes(path);

            String mimeType = Files.probeContentType(path);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // POST
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        if(productRepository.existsByProductNameAndProductDescription(product.getProductName(), product.getProductDescription()))
            throw new IllegalStateException("Product with name: " + product.getProductName() + 
            " and description: " + product.getProductDescription() + " already exists!");
        else{
            product.getDetails().forEach(detail -> detail.setProduct(product));
            product.setProductDayCreated(new Date());

            Product savedProduct = productRepository.save(product);
            return convertToDTO(savedProduct);
        }
    }

    public String uploadImages(String productName, MultipartFile[] files) {
        Path uploadDirPath = Path.of("temporaryDisabled/img/products/", productName);
        
        try {
            Files.createDirectories(uploadDirPath);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create upload directory: " + uploadDirPath.toString(), e);
        }
    
        for (MultipartFile file : files) {
            @SuppressWarnings("null")
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path filePath = uploadDirPath.resolve(fileName);
            
            try {
                file.transferTo(filePath.toFile());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to upload file: " + fileName, e);
            }
        }
        
        return "Upload Images successfully";
    }


    // DELETE
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepository.delete(product);
    }

    // PUT
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product productToUpdate = productRepository.findById(productDTO.getId())
            .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        // Update product fields
        productToUpdate.setProductName(productDTO.getProductName());
        productToUpdate.setProductDescription(productDTO.getProductDescription());
        productToUpdate.setProductImage(productDTO.getProductImage());
        productToUpdate.setProductPrice(productDTO.getProductPrice());
        productToUpdate.setProductQuantity(productDTO.getProductQuantity());
        productToUpdate.setProductQuantitySold(productDTO.getProductQuantitySold());
        productToUpdate.setProductDayCreated(productDTO.getProductDayCreated());
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

        return convertToDTO(productRepository.save(productToUpdate));
    }
}
