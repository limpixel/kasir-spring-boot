package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "APIs for managing products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Add a new product to the inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or product already exists")
    })
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update an existing product's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input or product name already exists")
    })
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id,
            @RequestBody Product productDetails) {
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Remove a product from the inventory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name", description = "Find products containing the specified name")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<List<Product>> searchProducts(
            @Parameter(description = "Product name to search for", required = true) 
            @RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    @Operation(summary = "Get products by price range", description = "Find products within a specified price range")
    @ApiResponse(responseCode = "200", description = "Products found successfully")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @Parameter(description = "Minimum price", required = true) @RequestParam Integer minPrice,
            @Parameter(description = "Maximum price", required = true) @RequestParam Integer maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/in-stock")
    @Operation(summary = "Get in-stock products", description = "Retrieve all products that are currently in stock")
    @ApiResponse(responseCode = "200", description = "In-stock products retrieved successfully")
    public ResponseEntity<List<Product>> getInStockProducts() {
        List<Product> products = productService.getInStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock/{threshold}")
    @Operation(summary = "Get low stock products", description = "Find products with stock below the specified threshold")
    @ApiResponse(responseCode = "200", description = "Low stock products retrieved successfully")
    public ResponseEntity<List<Product>> getLowStockProducts(
            @Parameter(description = "Stock threshold", required = true) @PathVariable Integer threshold) {
        List<Product> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get out of stock products", description = "Retrieve all products that are currently out of stock")
    @ApiResponse(responseCode = "200", description = "Out of stock products retrieved successfully")
    public ResponseEntity<List<Product>> getOutOfStockProducts() {
        List<Product> products = productService.getOutOfStockProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/stats/in-stock-count")
    @Operation(summary = "Get in-stock product count", description = "Get the count of products that are currently in stock")
    @ApiResponse(responseCode = "200", description = "In-stock count retrieved successfully")
    public ResponseEntity<Long> getInStockCount() {
        long count = productService.getInStockCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/check-availability/{id}")
    @Operation(summary = "Check product availability", description = "Check if a product is available in the specified quantity")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Availability checked successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Boolean> checkAvailability(
            @Parameter(description = "Product ID", required = true) @PathVariable Long id,
            @Parameter(description = "Required quantity", required = true) @RequestParam Integer quantity) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        boolean available = productService.isProductAvailable(id, quantity);
        return ResponseEntity.ok(available);
    }
}