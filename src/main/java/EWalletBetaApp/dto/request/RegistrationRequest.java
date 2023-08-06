package EWalletBetaApp.dto.request;

import EWalletBetaApp.data.models.Address;
import EWalletBetaApp.data.models.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {
    private String firstName;
    private String gender;
    private String lastName;
    private String email;
    private String userName;
    private Address address;
    private String phoneNumber;
    private String accountNumber;
    private String pin;
    private String passWord;

}
