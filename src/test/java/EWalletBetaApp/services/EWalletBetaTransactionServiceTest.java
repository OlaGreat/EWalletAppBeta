package EWalletBetaApp.services;

import EWalletBetaApp.data.models.Transaction;
import EWalletBetaApp.dto.request.TransactionRequest;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.enums.TransactionStatus;
import EWalletBetaApp.enums.TransactionType;
import EWalletBetaApp.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EWalletBetaTransactionServiceTest {
   @Autowired
    TransactionService transactionService;
    TransactionRequest request;

    @BeforeEach
    void setUp() {
        request = buildTransaction();
    }

    @Test
    void testCreateTransaction() {
        TransactionRequest newRequest = buildTransaction2();
        TransactionResponse savedTransaction2 = transactionService.createTransaction(newRequest);
       TransactionResponse savedTransaction = transactionService.createTransaction(request);
       assertNotNull(savedTransaction);
       assertNotNull(savedTransaction2);
    }

    @Test
    void findById() throws TransactionNotFoundException {
        TransactionResponse savedTransaction = transactionService.createTransaction(request);
        Transaction transaction = transactionService.findById(1L);
        assertNotNull(transaction);
    }

    @Test
    void deleteById() throws TransactionNotFoundException {
        TransactionResponse savedTransaction = transactionService.createTransaction(request);
        assertNotNull(savedTransaction);
        transactionService.deleteById(1L);
        assertThrows(TransactionNotFoundException.class,()-> transactionService.findById(1L));
    }

    @Test
    void findByDate() {
        TransactionResponse savedTransaction = transactionService.createTransaction(request);
        assertNotNull(savedTransaction);
        List<Transaction> transactions = transactionService.findByDate("2023-08-02");
        assertNotNull(transactions);
        System.out.println(transactions);
    }


    public TransactionRequest buildTransaction(){
        TransactionRequest request = new TransactionRequest();
        request.setAmount(BigDecimal.valueOf(4000.00));
        request.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        request.setTransactionType(TransactionType.CREDIT);
        return request;
    }
    public TransactionRequest buildTransaction2(){
        TransactionRequest request2 = new TransactionRequest();
        request2.setAmount(BigDecimal.valueOf(5000.00));
        request2.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        request2.setTransactionType(TransactionType.CREDIT);
        return request2;
    }
}