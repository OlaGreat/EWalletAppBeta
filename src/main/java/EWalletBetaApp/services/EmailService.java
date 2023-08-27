package EWalletBetaApp.services;

import EWalletBetaApp.dto.request.EmailNotificationRequest;
import EWalletBetaApp.dto.response.EmailNotificationResponse;

public interface EmailService {
    EmailNotificationResponse send(EmailNotificationRequest emailNotificationRequest);

}
