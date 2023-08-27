package EWalletBetaApp.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmailNotificationRequest {
    private List<Recipient> recipients;
    private List<String> copiedEmails;
    private String mailContent;
    private String textContent;
    private String subject;
    private String sender;



}
