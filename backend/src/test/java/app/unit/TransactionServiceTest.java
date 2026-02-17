package app.unit;

import app.transaction.model.Transaction;
import app.transaction.repo.TransactionRepository;
import app.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import app.transaction.dto.EditTransactionsRequest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void getTransactionsForUser_callsRepositoryFindByUserId() {
        when(transactionRepository.findByUserId(1)).thenReturn(List.of());

        List<Transaction> result = transactionService.getTransactionsForUser(1);

        assertNotNull(result);
        verify(transactionRepository).findByUserId(1);
    }

    @Test
    void addTransaction_callsRepositorySave() {
        Transaction tx = new Transaction();
        tx.setAmount(new BigDecimal("10.00"));
        tx.setTransactionDate(LocalDateTime.now());
        tx.setMerchant("Test");
        tx.setCategory("Food");

        transactionService.addTransaction(tx);

        verify(transactionRepository).save(tx);
    }

    @Test
    void deleteMany_callsRepositoryDeleteAllById() {
        List<Integer> ids = List.of(1, 2, 3);

        transactionService.deleteMany(ids);

        verify(transactionRepository).deleteAllById(ids);
    }

    @Test
    void editTransactions_updatesFields_andSavesAll() {
        Transaction existing = new Transaction();
        existing.setMerchant("Old");
        existing.setCategory("Old");
        existing.setAmount(new BigDecimal("1.00"));
        existing.setTransactionDate(LocalDateTime.parse("2026-02-16T10:00:00"));

        when(transactionRepository.findAllById(List.of(7))).thenReturn(List.of(existing));

        EditTransactionsRequest.Updates updates = new EditTransactionsRequest.Updates();
        updates.setMerchant("New Merchant");
        updates.setCategory("New Category");
        updates.setAmount(new BigDecimal("99.99"));
        updates.setTransactionDate(LocalDateTime.parse("2026-02-16T11:00:00"));

        transactionService.editTransactions(List.of(7), updates);


        assertEquals("New Merchant", existing.getMerchant());
        assertEquals("New Category", existing.getCategory());
        assertEquals(new BigDecimal("99.99"), existing.getAmount());
        assertEquals(LocalDateTime.parse("2026-02-16T11:00:00"), existing.getTransactionDate());

        ArgumentCaptor<List<Transaction>> captor = ArgumentCaptor.forClass(List.class);
        verify(transactionRepository).saveAll(captor.capture());
        assertEquals(1, captor.getValue().size());
    }
}
