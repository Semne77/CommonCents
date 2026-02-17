package app.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionRequest {
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String merchant;
    private String category;

    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public String getMerchant() { return merchant; }
    public String getCategory() { return category; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public void setMerchant(String merchant) { this.merchant = merchant; }
    public void setCategory(String category) { this.category = category; }
}
