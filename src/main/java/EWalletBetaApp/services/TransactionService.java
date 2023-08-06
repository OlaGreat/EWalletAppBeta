package EWalletBetaApp.services;

import EWalletBetaApp.data.models.Transaction;
import EWalletBetaApp.dto.request.TransactionRequest;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.exceptions.TransactionNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionResponse createTransaction (TransactionRequest transactionRequest);
    Transaction findById (Long id) throws TransactionNotFoundException;
    void deleteById(Long id) throws TransactionNotFoundException;
    List<Transaction> findByDate(String date);
}
