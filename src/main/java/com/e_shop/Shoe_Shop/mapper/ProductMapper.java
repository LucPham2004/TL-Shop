package com.e_shop.Shoe_Shop.mapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.e_shop.Shoe_Shop.dto.dto.ProductWithDetails;
import com.e_shop.Shoe_Shop.dto.dto.ProductDTO;
import com.e_shop.Shoe_Shop.dto.dto.ProductFullInfo;
import com.e_shop.Shoe_Shop.entity.Brand;
import com.e_shop.Shoe_Shop.entity.Category;
import com.e_shop.Shoe_Shop.entity.Media;
import com.e_shop.Shoe_Shop.entity.Product;
import com.e_shop.Shoe_Shop.entity.ProductDetail;
import com.e_shop.Shoe_Shop.repository.BrandRepository;
import com.e_shop.Shoe_Shop.repository.CategoryRepository;
import com.e_shop.Shoe_Shop.repository.MediaRepository;

@Component
public class ProductMapper {
    
    public ProductMapper(BrandRepository brandRepository, CategoryRepository categoryRepository,
            MediaRepository mediaRepository) {
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.mediaRepository = mediaRepository;
    }

    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final MediaRepository mediaRepository;
    
    // Convert Product and ProductDetail to DTO
    public ProductWithDetails convertToDetailsDTO(Product product) {
        Set<ProductWithDetails.ProductDetailDTO> detailDTOs = product.getDetails().stream()
            .map(this::convertDetailToDTO)
            .collect(Collectors.toSet());

        ProductWithDetails dto = new ProductWithDetails();

        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductImage(product.getProductImage());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductQuantity(product.getProductQuantity());
        dto.setProductQuantitySold(product.getProductQuantitySold());
        dto.setProductDayCreated(product.getProductDayCreated());
        dto.setDiscountPercent(product.getDiscountPercent());
        dto.setReviewCount(product.getReviewCount());
        dto.setAverageRating(product.getAverageRating());
        dto.setBrandName(product.getBrand().getName());
        dto.setCategories(product.getCategory().stream().map(Category::getName).collect(Collectors.toSet()));
        dto.setDetails(detailDTOs);

        Set<Media> medias = product.getMedias();
        if (medias != null && !medias.isEmpty()) {
            String[] publicIds = medias.stream()
                .map(Media::getPublicId)
                .toArray(String[]::new);

            String[] urls = medias.stream()
                .map(Media::getUrl)
                .toArray(String[]::new);

            dto.setPublicIds(publicIds);
            dto.setUrls(urls);
        }

        return dto;
    }

    public ProductWithDetails.ProductDetailDTO convertDetailToDTO(ProductDetail productDetail) {
        return new ProductWithDetails.ProductDetailDTO(
            productDetail.getId(),
            productDetail.getColor(),
            productDetail.getSize(),
            productDetail.getQuantity(),
            productDetail.getQuantitySold()
        );
    }

    // Convert DTO to Product and ProductDetail Entity
    public Product convertToEntity(ProductWithDetails productDTO) {
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

        if (productDTO.getPublicIds() != null && productDTO.getUrls() != null) {

            if (productDTO.getPublicIds().length != productDTO.getUrls().length) {
                throw new IllegalArgumentException("The size of publicIds and urls must be the same.");
            }
        
            Set<Media> medias = new HashSet<>();
        
            for (int i = 0; i < productDTO.getPublicIds().length; i++) {
                Media media = new Media();
                media.setPublicId(productDTO.getPublicIds()[i]);
                media.setUrl(productDTO.getUrls()[i]);
                media.setProduct(product);
        
                medias.add(mediaRepository.save(media));
            }
        
            product.setMedias(medias);
        }

        return product;
    }

    public ProductDetail convertDetailToEntity(ProductWithDetails.ProductDetailDTO productDetailDTO) {
        ProductDetail detail = new ProductDetail();
        detail.setId(productDetailDTO.getId());
        detail.setColor(productDetailDTO.getColor());
        detail.setSize(productDetailDTO.getSize());
        detail.setQuantity(productDetailDTO.getQuantity());
        detail.setQuantitySold(productDetailDTO.getQuantitySold());
        return detail;
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductImage(product.getProductImage());
        dto.setProductPrice(product.getProductPrice());
        dto.setDiscountPercent(product.getDiscountPercent());
        dto.setBrandName(product.getBrand().getName());
        dto.setCategories(product.getCategory().stream().map(Category::getName).collect(Collectors.toSet()));
        
        Set<Media> medias = product.getMedias();
        if (medias != null && !medias.isEmpty()) {
            String[] publicIds = medias.stream()
                .map(Media::getPublicId)
                .toArray(String[]::new);

            String[] urls = medias.stream()
                .map(Media::getUrl)
                .toArray(String[]::new);

            dto.setPublicIds(publicIds);
            dto.setUrls(urls);
        }
        
        return dto;
    }

    public ProductFullInfo convertToFullInfoDTO(Product product) {
        ProductFullInfo dto = new ProductFullInfo();

        dto.setId(product.getId());
        dto.setProductName(product.getProductName());
        dto.setProductDescription(product.getProductDescription());
        dto.setProductImage(product.getProductImage());
        dto.setProductPrice(product.getProductPrice());
        dto.setProductQuantity(product.getProductQuantity());
        dto.setProductQuantitySold(product.getProductQuantitySold());
        dto.setProductDayCreated(new Date());
        dto.setDiscountPercent(product.getDiscountPercent());
        dto.setReviewCount(product.getReviewCount());
        dto.setAverageRating(product.getAverageRating());
        dto.setBrandName(product.getBrand().getName());
        dto.setCategories(product.getCategory().stream().map(Category::getName).collect(Collectors.toSet()));
        
        Set<Media> medias = product.getMedias();
        if (medias != null && !medias.isEmpty()) {
            String[] publicIds = medias.stream()
                .map(Media::getPublicId)
                .toArray(String[]::new);

            String[] urls = medias.stream()
                .map(Media::getUrl)
                .toArray(String[]::new);

            dto.setPublicIds(publicIds);
            dto.setUrls(urls);
        }
        
        return dto;
    }
}
