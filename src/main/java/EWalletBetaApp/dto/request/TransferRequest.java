package EWalletBetaApp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class TransferRequest {
    private String receiverAccountNumber;
    private String depositorAccount;
    private BigDecimal transferAmount;
    private String pin;
}
