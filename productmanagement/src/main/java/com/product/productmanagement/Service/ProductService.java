package com.product.productmanagement.Service;

import com.product.productmanagement.Entity.Product;
import com.product.productmanagement.Exception.ProductNotFoundException;
import com.product.productmanagement.Model.ProductRequest;
import com.product.productmanagement.Model.ProductResponse;
import com.product.productmanagement.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PricingService pricingService;

    public ProductService(ProductRepository productRepository, PricingService pricingService) {
        this.productRepository = productRepository;
        this.pricingService = pricingService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product(
                request.getSku(),
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getQuantity(),
                request.getCategory()
        );
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    public ProductResponse toResponse(Product product) {
        double finalPrice = pricingService.finalPrice(product);
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                finalPrice,
                product.getQuantity(),
                product.getCategory()
        );
    }

    public void reduceQuantity(Long productId, int quantity) {
    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    if (product.getQuantity() < quantity) {
        throw new RuntimeException("Insufficient stock for product ID: " + productId);
    }

    product.setQuantity(product.getQuantity() - quantity);
    productRepository.save(product);
}

}

