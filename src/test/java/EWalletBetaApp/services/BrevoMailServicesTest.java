package EWalletBetaApp.services;

import EWalletBetaApp.dto.request.EmailNotificationRequest;
import EWalletBetaApp.dto.response.EmailNotificationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BrevoMailServicesTest {
    @Autowired
    private EmailService brevoMailService;

    @Test
    void testThatBrevoCanSendMail(){
        String recipient = "test@gamil.com";
        String content = "we are testing";
        String subject = "test";

        EmailNotificationRequest emailNotificationRequest = new EmailNotificationRequest();

        emailNotificationRequest.setRecipients("test@gmail.com");
        emailNotificationRequest.setMailContent();
        emailNotificationRequest.setSubject("TestMail");
        emailNotificationRequest.setTextContent();


        EmailNotificationResponse response = brevoMailService.send(emailNotificationRequest);

    }

}