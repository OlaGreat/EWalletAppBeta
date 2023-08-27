package EWalletBetaApp.controller;

import EWalletBetaApp.dto.request.RegistrationRequest;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.exceptions.UserAlreadyExistException;
import EWalletBetaApp.services.EWalletBetaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class EWalletBetaServiceController {
    EWalletBetaServices eWalletBetaServices;

    public ResponseEntity<RegistrationResponse> registerUserWallet(@RequestBody RegistrationRequest registrationRequest){
        try {
            var userResponse = eWalletBetaServices.register(registrationRequest);
        } catch (UserAlreadyExistException e) {
            throw new RuntimeException(e);
        }

    }

}
