package EWalletBetaApp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DepositRequest {
    private BigDecimal depositAmount;
    private String AccountNumber;

}
