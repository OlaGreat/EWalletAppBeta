package EWalletBetaApp.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegistrationResponse {
    private String accountNumber;
    private String message;
    private String userName;
    private BigDecimal balance;
}
