package EWalletBetaApp.data.models;

import EWalletBetaApp.enums.TransactionStatus;
import EWalletBetaApp.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    private LocalDate transactionDate = LocalDate.now();
    @ManyToOne
    @JoinColumn(name = "walletId")
    private Wallet walletId;
    private String transactionReferenceNumber;



}
