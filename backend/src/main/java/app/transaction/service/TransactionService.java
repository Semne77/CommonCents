package app.transaction.service;

import java.util.List;

import app.transaction.dto.EditTransactionsRequest;
import app.transaction.model.Transaction;
import app.transaction.repo.TransactionRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getTransactionsForUser(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    public void addTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(int id) {
        transactionRepository.deleteById(id);
    }

    public List<Transaction> saveAll(List<Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    public void deleteMany(List<Integer> ids) {
        transactionRepository.deleteAllById(ids);
    }

    @Transactional
    public void editTransactions(List<Integer> ids, EditTransactionsRequest.Updates updates) {
        List<Transaction> transactions = transactionRepository.findAllById(ids);

        for (Transaction tx : transactions) {
            if (updates.getMerchant() != null) {
                tx.setMerchant(updates.getMerchant());
            }
            if (updates.getCategory() != null) {
                tx.setCategory(updates.getCategory());
            }
            if (updates.getAmount() != null) {
                tx.setAmount(updates.getAmount());
            }
            if (updates.getTransactionDate() != null) {
                tx.setTransactionDate(updates.getTransactionDate());
            }
        }

        transactionRepository.saveAll(transactions);
    }
}
