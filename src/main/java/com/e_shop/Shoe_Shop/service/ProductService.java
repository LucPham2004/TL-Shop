package com.e_shop.Shoe_Shop.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.e_shop.Shoe_Shop.dto.dto.ProductDTO;
import com.e_shop.Shoe_Shop.dto.dto.ProductFullInfo;
import com.e_shop.Shoe_Shop.dto.dto.ProductWithDetails;
import com.e_shop.Shoe_Shop.dto.request.AddProductImages;
import com.e_shop.Shoe_Shop.dto.request.ProductEditRequest;
import com.e_shop.Shoe_Shop.entity.Brand;
import com.e_shop.Shoe_Shop.entity.Category;
import com.e_shop.Shoe_Shop.entity.Media;
import com.e_shop.Shoe_Shop.entity.Product;
import com.e_shop.Shoe_Shop.entity.ProductDetail;
import com.e_shop.Shoe_Shop.exception.AppException;
import com.e_shop.Shoe_Shop.exception.ErrorCode;
import com.e_shop.Shoe_Shop.mapper.ProductMapper;
import com.e_shop.Shoe_Shop.repository.BrandRepository;
import com.e_shop.Shoe_Shop.repository.CategoryRepository;
import com.e_shop.Shoe_Shop.repository.MediaRepository;
import com.e_shop.Shoe_Shop.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final MediaRepository mediaRepository;
    

    public ProductService(ProductRepository productRepository, BrandRepository brandRepository,
            CategoryRepository categoryRepository, ProductMapper productMapper, MediaRepository mediaRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.mediaRepository = mediaRepository;
    }
    // CRUD Methods
    // GET
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
        .map(productMapper::convertToDTO)
        .collect(Collectors.toList());
    }

    public List<ProductFullInfo> getAllProductsFullInfo() {
        return productRepository.findAll().stream()
        .map(productMapper::convertToFullInfoDTO)
        .collect(Collectors.toList());
    }

    public List<ProductWithDetails> getAllProductsWithDetails() {
        return productRepository.findAll().stream()
        .map(productMapper::convertToDetailsDTO)
        .collect(Collectors.toList());
    }

    public ProductWithDetails getProductById(Integer id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
        return productMapper.convertToDetailsDTO(product);
    }

    public List<ProductDTO> getProductsByBrand(String brandName) {
        boolean exists = brandRepository.existsByName(brandName);
        if (!exists) {
            throw new IllegalStateException("Brand with name: " + brandName + " doesn't exist!");
        }
        List<Product> products = productRepository.findByBrandName(brandName);
        return products.stream()
                .map(productMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String categoryName) {
        boolean exists = categoryRepository.existsByName(categoryName);
        if (!exists) {
            throw new IllegalStateException("Category with name: " + categoryName + " doesn't exist!");
        }
        List<Product> products = productRepository.findByCategoryName(categoryName);
        return products.stream()
                .map(productMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    // Return top-seller, on-sale products
    public List<ProductDTO> getTopProducts() {
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
        
        return resultList.stream().map(productMapper::convertToDTO).collect(Collectors.toList());
    }

    // Search products
    public List<ProductDTO> searchProducts(String keywword) {
        return productRepository.findByProductNameContainingOrProductDescriptionContainingOrCategoryNameOrBrandNameContaining(
            keywword, keywword, keywword, keywword).stream()
        .map(productMapper::convertToDTO)
        .collect(Collectors.toList());
    }

    public List<ProductDTO> searchProductsByIdContaining(int id) {
        return productRepository.findByIdContaining(id).stream()
        .map(productMapper::convertToDTO)
        .collect(Collectors.toList());
    }

    public List<ProductDTO> lowRemainingProducts() {
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
        .map(productMapper::convertToDTO)
        .collect(Collectors.toList());
    }

    // // Get product Images
    // public ResponseEntity<byte[]> getProductImages(String productName) {
    //     try {
    //         String uploadDir = "temporaryDisabled/img/products/";
    //         Path path = Path.of(uploadDir + productName);

    //         if (!Files.exists(path)) {
    //             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    //         }

    //         byte[] imageBytes = Files.readAllBytes(path);

    //         String mimeType = Files.probeContentType(path);

    //         return ResponseEntity.ok()
    //                 .contentType(MediaType.parseMediaType(mimeType))
    //                 .body(imageBytes);
    //     } catch (IOException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    //     }
    // }

    // POST
    public ProductWithDetails saveProduct(ProductWithDetails productDTO) {
        Product product = productMapper.convertToEntity(productDTO);
        if(productRepository.existsByProductNameAndProductDescription(product.getProductName(), product.getProductDescription()))
            throw new AppException(ErrorCode.ENTITY_EXISTED);
        else{
            product.getDetails().forEach(detail -> detail.setProduct(product));
            product.setProductDayCreated(new Date());

            return productMapper.convertToDetailsDTO(productRepository.save(product));
        }
    }

    public ProductFullInfo uploadImages(AddProductImages request) {
        Product product = productRepository.findById(request.getId())
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_EXISTED));

        if (request.getPublicIds() != null && request.getUrls() != null) {

            if (request.getPublicIds().length != request.getUrls().length) {
                throw new IllegalArgumentException("The size of publicIds and urls must be the same.");
            }
        
            Set<Media> medias = new HashSet<>();
        
            for (int i = 0; i < request.getPublicIds().length; i++) {
                Media media = new Media();
                media.setPublicId(request.getPublicIds()[i]);
                media.setUrl(request.getUrls()[i]);
                media.setProduct(product);
        
                medias.add(mediaRepository.save(media));
            }
        
            product.setMedias(medias);
        }

        return productMapper.convertToFullInfoDTO(product);
    }


    // DELETE
    public void deleteProduct(Integer id) {
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));
        productRepository.delete(product);
    }

    // PUT
    @Transactional
    public ProductWithDetails updateProduct(ProductWithDetails productDTO) {
        Product productToUpdate = productRepository.findById(productDTO.getId())
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));

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
            .map(productMapper::convertDetailToEntity)
            .collect(Collectors.toSet());
        productToUpdate.getDetails().clear();
        productToUpdate.getDetails().addAll(updatedDetails);
        updatedDetails.forEach(detail -> detail.setProduct(productToUpdate));

        return productMapper.convertToDetailsDTO(productRepository.save(productToUpdate));
    }

    @Transactional
    public ProductWithDetails editProductMainInfo(ProductEditRequest productDTO) {
        Product productToUpdate = productRepository.findById(productDTO.getId())
            .orElseThrow(() -> new AppException(ErrorCode.ENTITY_NOT_EXISTED));

        // Update product fields
        productToUpdate.setProductName(productDTO.getProductName());
        productToUpdate.setProductDescription(productDTO.getProductDescription());
        productToUpdate.setProductPrice(productDTO.getProductPrice());
        productToUpdate.setDiscountPercent(productDTO.getDiscountPercent());

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

        return productMapper.convertToDetailsDTO(productRepository.save(productToUpdate));
    }
}
