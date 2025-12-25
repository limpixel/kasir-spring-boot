package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        if (productRepository.existsByName(product.getName())) {
            throw new RuntimeException("Product with name '" + product.getName() + "' already exists");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if (!product.getName().equals(productDetails.getName()) && 
            productRepository.existsByName(productDetails.getName())) {
            throw new RuntimeException("Product with name '" + productDetails.getName() + "' already exists");
        }

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> getProductsByPriceRange(Integer minPrice, Integer maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Product> getInStockProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findByStockLessThan(threshold);
    }

    public List<Product> getOutOfStockProducts() {
        return productRepository.findOutOfStockProducts();
    }

    public long getInStockCount() {
        return productRepository.countInStockProducts();
    }

    public boolean isProductAvailable(Long id, Integer quantity) {
        Optional<Product> product = productRepository.findById(id);
        return product.isPresent() && product.get().getStock() >= quantity;
    }

    public void updateStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        if (product.getStock() + quantity < 0) {
            throw new RuntimeException("Insufficient stock");
        }
        
        product.setStock(product.getStock() + quantity);
        productRepository.save(product);
    }
}