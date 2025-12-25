package com.example.demo.repository;

import com.example.demo.model.Transaction;
import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByProductId(Long productId);
    
    List<Transaction> findByProduct(Product product);
    
    List<Transaction> findByTransactionType(String transactionType);
    
    List<Transaction> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Transaction> findByCreatedAtAfter(LocalDateTime startDate);
    
    List<Transaction> findByCreatedAtBefore(LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.product.id = :productId AND t.transactionType = :type")
    List<Transaction> findByProductAndTransactionType(@Param("productId") Long productId, @Param("type") String type);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.transactionType = :type")
    long countByTransactionType(@Param("type") String type);
    
    @Query("SELECT SUM(t.totalPrice) FROM Transaction t WHERE t.transactionType = 'SALE'")
    java.math.BigDecimal sumTotalSales();
    
    @Query("SELECT SUM(t.totalPrice) FROM Transaction t WHERE t.transactionType = 'PURCHASE'")
    java.math.BigDecimal sumTotalPurchases();
    
    List<Transaction> findTop10ByOrderByCreatedAtDesc();
}