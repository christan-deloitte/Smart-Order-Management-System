package com.product.productmanagement.Controller;

import com.product.productmanagement.Entity.Product;
import com.product.productmanagement.Exception.ProductNotFoundException;
import com.product.productmanagement.Model.ApiResponse;
import com.product.productmanagement.Model.ProductRequest;
import com.product.productmanagement.Model.ProductResponse;
import com.product.productmanagement.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /products/all — fetch all products
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(HttpStatus.NO_CONTENT.value(),
                            "No products found", null));
        }

        List<ProductResponse> responseList = products.stream()
                .map(productService::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Products retrieved successfully", responseList)
        );
    }

    /**
     * POST /products — create a new product
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody @Valid ProductRequest request) {
        Product product = productService.createProduct(request);
        ProductResponse response = productService.toResponse(product);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Product created successfully", response));
    }

    /**
     * GET /products/{id} — fetch product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        ProductResponse response = productService.toResponse(product);

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Product retrieved successfully", response)
        );
    }

    @PutMapping("/{id}/reduce")
    public ResponseEntity<ApiResponse<String>> reduceQuantity(
        @PathVariable Long id,
        @RequestParam int quantity) {

    productService.reduceQuantity(id, quantity);
    return ResponseEntity.ok(new ApiResponse<>(200, "Quantity updated successfully", "Updated"));
}

}
