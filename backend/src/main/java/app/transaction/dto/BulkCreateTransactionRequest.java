package app.transaction.dto;

import java.util.List;

public class BulkCreateTransactionRequest {
    private List<CreateTransactionRequest> transactions;

    public List<CreateTransactionRequest> getTransactions() { return transactions; }
    public void setTransactions(List<CreateTransactionRequest> transactions) { this.transactions = transactions; }
}
