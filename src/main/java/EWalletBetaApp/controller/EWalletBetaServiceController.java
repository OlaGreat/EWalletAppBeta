package EWalletBetaApp.controller;

import EWalletBetaApp.dto.request.RegistrationRequest;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.exceptions.UserAlreadyExistException;
import EWalletBetaApp.services.EWalletBetaServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class EWalletBetaServiceController {
    EWalletBetaServices eWalletBetaServices;

    @PostMapping
    public ResponseEntity<RegistrationResponse> registerUserWallet(@RequestBody RegistrationRequest registrationRequest) throws UserAlreadyExistException {
            RegistrationResponse userResponse = eWalletBetaServices.register(registrationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<String> checkBalance(String pin, @PathVariable long id){
        String balance = eWalletBetaServices.checkBalance(pin, id);
        return ResponseEntity.ok(balance);
    }


}
