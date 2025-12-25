package com.example.demo.service;

import com.example.demo.model.Transaction;
import com.example.demo.model.Product;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public Transaction createSale(Long productId, Integer quantity, String description) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock. Available: " + product.getStock() + ", Requested: " + quantity);
        }

        BigDecimal totalPrice = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity));
        
        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setQuantity(quantity);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionType("SALE");
        transaction.setDescription(description);
        transaction.setCreatedAt(LocalDateTime.now());

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        return transactionRepository.save(transaction);
    }

    public Transaction createPurchase(Long productId, Integer quantity, String description) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        BigDecimal totalPrice = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity));
        
        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setQuantity(quantity);
        transaction.setTotalPrice(totalPrice);
        transaction.setTransactionType("PURCHASE");
        transaction.setDescription(description);
        transaction.setCreatedAt(LocalDateTime.now());

        product.setStock(product.getStock() + quantity);
        productRepository.save(product);

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getProduct() == null || transaction.getProduct().getId() == null) {
            throw new RuntimeException("Product is required");
        }

        Product product = productRepository.findById(transaction.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + transaction.getProduct().getId()));

        transaction.setProduct(product);
        
        if ("SALE".equals(transaction.getTransactionType())) {
            if (product.getStock() < transaction.getQuantity()) {
                throw new RuntimeException("Insufficient stock for sale");
            }
            product.setStock(product.getStock() - transaction.getQuantity());
        } else if ("PURCHASE".equals(transaction.getTransactionType())) {
            product.setStock(product.getStock() + transaction.getQuantity());
        }

        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(LocalDateTime.now());
        }

        productRepository.save(product);
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(Long id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        transaction.setProduct(transactionDetails.getProduct());
        transaction.setQuantity(transactionDetails.getQuantity());
        transaction.setTotalPrice(transactionDetails.getTotalPrice());
        transaction.setTransactionType(transactionDetails.getTransactionType());
        transaction.setDescription(transactionDetails.getDescription());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
    }

    public List<Transaction> getTransactionsByProductId(Long productId) {
        return transactionRepository.findByProductId(productId);
    }

    public List<Transaction> getTransactionsByType(String transactionType) {
        return transactionRepository.findByTransactionType(transactionType);
    }

    public List<Transaction> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public List<Transaction> getRecentTransactions() {
        return transactionRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public long getTransactionCountByType(String transactionType) {
        return transactionRepository.countByTransactionType(transactionType);
    }

    public BigDecimal getTotalSales() {
        return transactionRepository.sumTotalSales();
    }

    public BigDecimal getTotalPurchases() {
        return transactionRepository.sumTotalPurchases();
    }

    public BigDecimal getNetRevenue() {
        BigDecimal totalSales = getTotalSales();
        BigDecimal totalPurchases = getTotalPurchases();
        return totalSales != null && totalPurchases != null ? totalSales.subtract(totalPurchases) : BigDecimal.ZERO;
    }
}