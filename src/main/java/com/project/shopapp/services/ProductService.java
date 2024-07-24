package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.dtos.response.ProductResponse;
import com.project.shopapp.mapper.ProductMapper;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import com.project.shopapp.repositories.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductMapper productMapper;
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductImageRepository productImageRepository;

    public Product createProduct(ProductDTO productDTO) {
        //check category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productMapper.toProduct(productDTO);
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    //get products paginated
    public Page<ProductResponse> getProductsPaginated(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(productMapper::toProductResponse);
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
    public Product updateProduct(Long id, ProductDTO productDTO) {
        // Check if product exists
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check category
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update existing product fields using mapper
        productMapper.updateProductFromDto(productDTO, existingProduct);

        // Set the category manually as it's ignored in the mapper
        existingProduct.setCategory(category);

        // Save and return the updated product
        return productRepository.save(existingProduct);
    }
    public void deleteProduct(Long id) {
        //check product exist
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
    public ProductImage createProductImage(Long id, ProductImageDTO productImageDTO) {
        Product existsProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        ProductImage newProductImage = ProductImage.builder()
                .imageUrl(productImageDTO.getImageUrl())
                .product(existsProduct)
                .build();
        //limit 5 images
        if(productImageRepository.findByProductId(id).size() >= 5){
            throw new RuntimeException("Product can only have 5 images");
        }
        return productImageRepository.save(newProductImage);
    }
}
