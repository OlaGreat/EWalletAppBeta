package EWalletBetaApp.data.repository;

import EWalletBetaApp.data.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EWalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByPhoneNumber(String phoneNumber);

    Optional<Wallet> findWalletByUserName(String userName);

    Optional<Wallet> findByEmail(String email);

    Optional<Wallet> findByAccountNumber(String accountNumber);

}
