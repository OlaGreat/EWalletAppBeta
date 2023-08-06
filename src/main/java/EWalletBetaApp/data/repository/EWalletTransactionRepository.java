package EWalletBetaApp.data.repository;

import EWalletBetaApp.data.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EWalletTransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionByTransactionDate(LocalDate date);

}
