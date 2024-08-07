package com.e_shop.Shoe_Shop.Entity.product;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.e_shop.Shoe_Shop.Entity.brand.Brand;
import com.e_shop.Shoe_Shop.Entity.brand.BrandRepository;
import com.e_shop.Shoe_Shop.Entity.category.CategoryRepository;
import com.e_shop.Shoe_Shop.Entity.category.Category;
import com.e_shop.Shoe_Shop.Entity.product.detail.ProductDetail;
import com.e_shop.Shoe_Shop.DTO.dto.ProductDTO;
import com.e_shop.Shoe_Shop.DTO.dto.ProductDTO.ProductDetailDTO;

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

    // Return top-seller, favorite, on-sale and... most costly products
    public List<ProductDTO> getTopProducts() {
        List<ProductDTO> allproducts = productRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
        List<ProductDTO> resultList = new ArrayList<>();
        int index = 0;

        allproducts.sort(Comparator.comparingInt(product -> product.getProductQuantitySold()));
        for(ProductDTO productDTO: allproducts) {
            if(index >= 4)
                break;
            resultList.add(productDTO);
            index++;
        }

        index = 0;
        allproducts.sort(Comparator.comparingDouble(product -> product.getAverageRating()));
        for(ProductDTO productDTO: allproducts) {
            if(index >= 4)
                break;
            resultList.add(productDTO);
            index++;
        }

        index = 0;
        allproducts.sort(Comparator.comparingDouble(product -> product.getDiscountPercent()));
        for(ProductDTO productDTO: allproducts) {
            if(index >= 8)
                break;
            resultList.add(productDTO);
            index++;
        }

        index = 0;
        allproducts.sort(Comparator.comparingDouble(product -> product.getProductPrice()));
        for(ProductDTO productDTO: allproducts) {
            if(index >= 4)
                break;
            resultList.add(productDTO);
            index++;
        }
        
        return resultList;
    }

    // Search products
    public List<ProductDTO> searchProducts(String keywword) {
        return productRepository.findByProductNameContainingOrProductDescriptionContainingOrCategoryName(
            keywword, keywword, keywword).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
    }

    public List<ProductDTO> lowRemainingProducts() {
        List<ProductDTO> products = productRepository.findAll().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());

        List<ProductDTO> lowRemainingProducts = new ArrayList<>();

        for(ProductDTO productDTO: products) {
            Set<ProductDetailDTO> productDetailsList = productDTO.getDetails();
            for(ProductDetailDTO productDetailsDTO: productDetailsList) {
                if (productDetailsDTO.getQuantity() < 20) {
                    lowRemainingProducts.add(productDTO);
                    break;
                }
            }
        }

        return lowRemainingProducts;
    }

    // Get product Images
    public ResponseEntity<byte[]> getProductImages(String productName) {
        try {
            String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/products/";
            Path path = Paths.get(uploadDir + productName);

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

    @SuppressWarnings("null")
    public String uploadImages(String productName, MultipartFile[] files) {
        String uploadDir = "C:/Users/ADMIN/Documents/Projects/TL-Shop/Shoe_Store/src/main/resources/static/img/products/" + productName;
    
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
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            
            if (fileName.contains("..")) {
                throw new IllegalStateException("Invalid file path: " + fileName);
            }
    
            String[] allowedExtensions = { "png", "jpg", "webp", "jpeg", "avif" };
            String fileExtension = StringUtils.getFilenameExtension(fileName);
            if (!Arrays.asList(allowedExtensions).contains(fileExtension.toLowerCase())) {
                throw new IllegalStateException("Invalid file extension: " + fileExtension);
            }
    
            String filePath = uploadDir + "/" + fileName;
            
            try {
                Path tempFile = Files.createTempFile(uploadDirFile.toPath(), null, "." + fileExtension);
                file.transferTo(tempFile.toFile());
                
                Files.move(tempFile, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException("Failed to upload image: " + fileName);
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
