package EWalletBetaApp.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${mail-api-key}")
    String apiKey;

    public String getApiKey(){
        return this.apiKey;
    }

}
