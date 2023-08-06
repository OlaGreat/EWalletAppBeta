package EWalletBetaApp.dto.request;

import EWalletBetaApp.data.models.Wallet;
import EWalletBetaApp.enums.TransactionStatus;
import EWalletBetaApp.enums.TransactionType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class TransactionRequest {
    private TransactionType transactionType;
    private BigDecimal amount;
    private TransactionStatus transactionStatus;
    private Wallet walletId;
}
