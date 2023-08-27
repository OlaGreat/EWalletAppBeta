package EWalletBetaApp.services;

import EWalletBetaApp.data.models.Transaction;
import EWalletBetaApp.data.repository.EWalletTransactionRepository;
import EWalletBetaApp.dto.request.TransactionRequest;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.exceptions.TransactionNotFoundException;
import EWalletBetaApp.utils.Generate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EWalletBetaTransactionService implements TransactionService{
   private final EWalletTransactionRepository transactionRepository;


    @Autowired
    public EWalletBetaTransactionService(EWalletTransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }


    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionStatus(transactionRequest.getTransactionStatus());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setAmount(transactionRequest.getAmount());
//        transaction.setWalletId(transactionRequest.getWalletId());
        transaction.setTransactionReferenceNumber(Generate.generateReferenceNumber());

        Transaction savedTransaction  = transactionRepository.save(transaction);

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionId(savedTransaction.getTransactionReferenceNumber());
        transactionResponse.setTransactionStatus(savedTransaction.getTransactionStatus());
        transactionResponse.setTransactionType(savedTransaction.getTransactionType());
        transactionResponse.setAmount(savedTransaction.getAmount());
        transactionResponse.setTransactionDate(savedTransaction.getTransactionDate());

        return transactionResponse;
    }

    @Override
    public Transaction findById(Long id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
                                    .orElseThrow(()->new TransactionNotFoundException("No Transaction with id " +id));
    }

    @Override
    public void deleteById(Long id) throws TransactionNotFoundException {
        Transaction foundTransaction = findById(id);
        if (foundTransaction != null){
            transactionRepository.deleteById(id);
        }
    }

    @Override
    public List<Transaction> findByDate(String date) {
        LocalDate transactionDate = LocalDate.parse(date);
        List<Transaction> transactions = new ArrayList<>();
        if (transactionRepository.findTransactionByTransactionDate(transactionDate).size() > 0){
            transactions = transactionRepository.findTransactionByTransactionDate(transactionDate);
            return transactions;
        }
        return transactions;
    }
}
