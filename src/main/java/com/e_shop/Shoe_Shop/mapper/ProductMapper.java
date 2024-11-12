package com.e_shop.Shoe_Shop.mapper;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.e_shop.Shoe_Shop.dto.dto.ProductDTO;
import com.e_shop.Shoe_Shop.dto.dto.ProductInfoDTO;
import com.e_shop.Shoe_Shop.entity.Brand;
import com.e_shop.Shoe_Shop.entity.Category;
import com.e_shop.Shoe_Shop.entity.Product;
import com.e_shop.Shoe_Shop.entity.ProductDetail;
import com.e_shop.Shoe_Shop.repository.BrandRepository;
import com.e_shop.Shoe_Shop.repository.CategoryRepository;

@Component
public class ProductMapper {
    
    public ProductMapper(BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    
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
}
