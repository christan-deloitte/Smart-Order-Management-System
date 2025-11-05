package com.product.productmanagement.Service;

import com.product.productmanagement.Entity.Product;
import com.product.productmanagement.Exception.ProductNotFoundException;
import com.product.productmanagement.Model.ProductRequest;
import com.product.productmanagement.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Fetch all products from the database
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Create a new product entry
     */
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

    /**
     * Fetch a specific product by ID
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }
}
