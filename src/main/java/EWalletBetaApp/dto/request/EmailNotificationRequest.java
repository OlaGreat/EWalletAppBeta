package EWalletBetaApp.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EmailNotificationRequest {

    @JsonProperty("to")
    private List<Recipient> recipients;

    @JsonProperty("cc")
    private List<String> copiedEmails;

    @JsonProperty("htmlcontent")
    private String mailContent;
    private String textContent;
    private String subject;
    private String sender;



}
