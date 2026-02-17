package app.transaction.controller;

import java.util.List;

import app.transaction.dto.EditTransactionsRequest;
import app.transaction.model.Transaction;
import app.transaction.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{userId}")
    public List<Transaction> getTransactions(@PathVariable int userId) {
        return transactionService.getTransactionsForUser(userId);
    }

    @PostMapping("/add")
    public void addTransaction(@RequestBody Transaction transaction) {
        transactionService.addTransaction(transaction);
    }

    @PostMapping("/bulk")
    public List<Transaction> bulkAdd(@RequestBody List<Transaction> transactions) {
        return transactionService.saveAll(transactions);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
    }

    @PostMapping("/deleteMany")
    public ResponseEntity<Void> deleteMany(@RequestBody List<Integer> ids) {
        transactionService.deleteMany(ids);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editTransactions(@RequestBody EditTransactionsRequest request) {
        transactionService.editTransactions(request.getIds(), request.getUpdates());
        return ResponseEntity.ok("Updated " + request.getIds().size() + " transactions.");
    }
}

