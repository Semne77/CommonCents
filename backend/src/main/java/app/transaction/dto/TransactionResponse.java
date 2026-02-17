package app.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private int id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String merchant;
    private String category;
    private int userId;
    private LocalDateTime createdAt;

    public int getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public String getMerchant() { return merchant; }
    public String getCategory() { return category; }
    public int getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public void setMerchant(String merchant) { this.merchant = merchant; }
    public void setCategory(String category) { this.category = category; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
