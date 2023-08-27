package EWalletBetaApp.services;

import EWalletBetaApp.configurations.AppConfig;
import EWalletBetaApp.dto.request.EmailNotificationRequest;
import EWalletBetaApp.dto.response.EmailNotificationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class BrevoMailServices implements EmailService{
    private final AppConfig appConfig;
    @Override
    public EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest) {
        String brevoMailAddress = "https://api.brevo.com/v3/smtp/email";

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key",appConfig.getApiKey());
        headers.set("content-type" , "application/json");

        HttpEntity<EmailNotificationRequest> requestHttpEntity = new HttpEntity<>(emailNotificationRequest, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<EmailNotificationResponse> response
                = restTemplate.postForEntity(brevoMailAddress, requestHttpEntity, EmailNotificationResponse.class);

        EmailNotificationResponse emailNotificationResponse =  response.getBody();
        return emailNotificationResponse;
    }
}
