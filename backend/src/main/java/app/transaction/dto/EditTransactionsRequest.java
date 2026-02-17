package app.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class EditTransactionsRequest {

    private List<Integer> ids;
    private Updates updates;

    public List<Integer> getIds() { return ids; }
    public Updates getUpdates() { return updates; }

    public void setIds(List<Integer> ids) { this.ids = ids; }
    public void setUpdates(Updates updates) { this.updates = updates; }

    public static class Updates {
        private String merchant;
        private String category;
        private BigDecimal amount;
        private LocalDateTime transactionDate;

        public String getMerchant() { return merchant; }
        public String getCategory() { return category; }
        public BigDecimal getAmount() { return amount; }
        public LocalDateTime getTransactionDate() { return transactionDate; }

        public void setMerchant(String merchant) { this.merchant = merchant; }
        public void setCategory(String category) { this.category = category; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    }
}

