package app.transaction.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    private String merchant;
    private String category;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Transaction() {}

    public Transaction(
            BigDecimal amount,
            LocalDateTime transactionDate,
            String merchant,
            String category,
            Integer userId,
            LocalDateTime createdAt
    ) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.merchant = merchant;
        this.category = category;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public String getMerchant() { return merchant; }
    public String getCategory() { return category; }
    public Integer getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Integer id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public void setMerchant(String merchant) { this.merchant = merchant; }
    public void setCategory(String category) { this.category = category; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
