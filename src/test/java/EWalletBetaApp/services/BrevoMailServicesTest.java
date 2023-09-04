package EWalletBetaApp.services;

import EWalletBetaApp.dto.request.EmailNotificationRequest;
import EWalletBetaApp.dto.request.Recipient;
import EWalletBetaApp.dto.request.Sender;
import EWalletBetaApp.dto.response.EmailNotificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class BrevoMailServicesTest {
    @Autowired
    private EmailService brevoMailService;

    @Test
    void testThatBrevoCanSendMail(){
        String html = "C:\\Users\\DELL\\Documents\\springProject\\promisciousApp\\E-WalletAppBeta" +
                "\\src\\main\\resources\\templates\\index.html";
        String recipient = "oladipupoolamilekan2@gmail.com";
        String mailContent = "<p>Dear Applicant</p>";
        String subject = "INTERVIEW";
        String textContent = "hiii";
        String sender = "OlaWallet@noreply.com";

        EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();

        emailNotificationRequest.setRecipients(List.of(new Recipient(recipient)));
        emailNotificationRequest.setMailContent(html);
        emailNotificationRequest.setSubject(subject);
        emailNotificationRequest.setTextContent(textContent);
        brevoMailService.send(emailNotificationRequest);


        EmailNotificationResponse response = brevoMailService.send(emailNotificationRequest);

        assertThat(response).isNotNull();

    }

}