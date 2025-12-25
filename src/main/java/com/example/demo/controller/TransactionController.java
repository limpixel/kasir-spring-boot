package com.example.demo.controller;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
@Tag(name = "Transaction Management", description = "APIs for managing transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieve a list of all transactions")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get transaction by ID", description = "Retrieve a specific transaction by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction found"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Transaction> getTransactionById(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a transaction", description = "Create a new transaction (sale or purchase)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock")
    })
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        try {
            Transaction createdTransaction = transactionService.createTransaction(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/sale")
    @Operation(summary = "Create a sale", description = "Create a sale transaction for a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Sale created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or insufficient stock"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Transaction> createSale(
            @Parameter(description = "Product ID", required = true) @RequestParam Long productId,
            @Parameter(description = "Quantity to sell", required = true) @RequestParam Integer quantity,
            @Parameter(description = "Sale description") @RequestParam(required = false) String description) {
        try {
            Transaction sale = transactionService.createSale(productId, quantity, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(sale);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/purchase")
    @Operation(summary = "Create a purchase", description = "Create a purchase transaction for a product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Purchase created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Transaction> createPurchase(
            @Parameter(description = "Product ID", required = true) @RequestParam Long productId,
            @Parameter(description = "Quantity to purchase", required = true) @RequestParam Integer quantity,
            @Parameter(description = "Purchase description") @RequestParam(required = false) String description) {
        try {
            Transaction purchase = transactionService.createPurchase(productId, quantity, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(purchase);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a transaction", description = "Update an existing transaction's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Transaction> updateTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id,
            @RequestBody Transaction transactionDetails) {
        try {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
            return ResponseEntity.ok(updatedTransaction);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a transaction", description = "Remove a transaction from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "Transaction ID", required = true) @PathVariable Long id) {
        try {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get transactions by product", description = "Retrieve all transactions for a specific product")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    public ResponseEntity<List<Transaction>> getTransactionsByProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable Long productId) {
        List<Transaction> transactions = transactionService.getTransactionsByProductId(productId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/type/{transactionType}")
    @Operation(summary = "Get transactions by type", description = "Retrieve all transactions of a specific type (SALE/PURCHASE)")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    public ResponseEntity<List<Transaction>> getTransactionsByType(
            @Parameter(description = "Transaction type (SALE/PURCHASE)", required = true) 
            @PathVariable String transactionType) {
        List<Transaction> transactions = transactionService.getTransactionsByType(transactionType);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get transactions by date range", description = "Retrieve all transactions within a specified date range")
    @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    public ResponseEntity<List<Transaction>> getTransactionsByDateRange(
            @Parameter(description = "Start date", required = true) 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date", required = true) 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/recent")
    @Operation(summary = "Get recent transactions", description = "Retrieve the 10 most recent transactions")
    @ApiResponse(responseCode = "200", description = "Recent transactions retrieved successfully")
    public ResponseEntity<List<Transaction>> getRecentTransactions() {
        List<Transaction> transactions = transactionService.getRecentTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/stats/count/{transactionType}")
    @Operation(summary = "Get transaction count by type", description = "Get the total count of transactions for a specific type")
    @ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    public ResponseEntity<Long> getTransactionCountByType(
            @Parameter(description = "Transaction type (SALE/PURCHASE)", required = true) 
            @PathVariable String transactionType) {
        long count = transactionService.getTransactionCountByType(transactionType);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/total-sales")
    @Operation(summary = "Get total sales amount", description = "Get the total amount from all sales transactions")
    @ApiResponse(responseCode = "200", description = "Total sales retrieved successfully")
    public ResponseEntity<BigDecimal> getTotalSales() {
        BigDecimal totalSales = transactionService.getTotalSales();
        return ResponseEntity.ok(totalSales);
    }

    @GetMapping("/stats/total-purchases")
    @Operation(summary = "Get total purchases amount", description = "Get the total amount from all purchase transactions")
    @ApiResponse(responseCode = "200", description = "Total purchases retrieved successfully")
    public ResponseEntity<BigDecimal> getTotalPurchases() {
        BigDecimal totalPurchases = transactionService.getTotalPurchases();
        return ResponseEntity.ok(totalPurchases);
    }

    @GetMapping("/stats/net-revenue")
    @Operation(summary = "Get net revenue", description = "Get the net revenue (total sales - total purchases)")
    @ApiResponse(responseCode = "200", description = "Net revenue retrieved successfully")
    public ResponseEntity<BigDecimal> getNetRevenue() {
        BigDecimal netRevenue = transactionService.getNetRevenue();
        return ResponseEntity.ok(netRevenue);
    }
}