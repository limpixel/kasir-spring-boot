package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByName(String name);
    
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);
    
    List<Product> findByStockGreaterThan(Integer stock);
    
    List<Product> findByStockLessThan(Integer stock);
    
    @Query("SELECT p FROM Product p WHERE p.stock = 0")
    List<Product> findOutOfStockProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE p.stock > 0")
    long countInStockProducts();
    
    boolean existsByName(String name);
}