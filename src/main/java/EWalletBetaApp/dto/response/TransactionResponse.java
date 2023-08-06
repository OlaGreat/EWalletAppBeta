package EWalletBetaApp.dto.response;

import EWalletBetaApp.data.models.Transaction;
import EWalletBetaApp.enums.TransactionStatus;
import EWalletBetaApp.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionResponse {
    private TransactionType transactionType;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private String TransactionId;
    private LocalDate transactionDate;
}
