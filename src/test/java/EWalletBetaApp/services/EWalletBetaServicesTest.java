package EWalletBetaApp.services;

import EWalletBetaApp.dto.request.DepositRequest;
import EWalletBetaApp.dto.request.LoginRequest;
import EWalletBetaApp.dto.request.RegistrationRequest;
import EWalletBetaApp.dto.request.TransferRequest;
import EWalletBetaApp.dto.response.LoginResponse;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.exceptions.InvalidAccountNumber;
import EWalletBetaApp.exceptions.LoginFailedException;
import EWalletBetaApp.exceptions.UserAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EWalletBetaServicesTest {
    @Autowired
    WalletService walletService;
    RegistrationRequest registrationRequest;


    @BeforeEach
   void setUp(){
        registrationRequest = buildWallet();
   }

    @Test
    void testRegisterNewWallet() throws UserAlreadyExistException {
        RegistrationResponse savedUser = walletService.register(registrationRequest);
        assertNotNull(savedUser);
        System.out.println(savedUser);

    }

    @Test
    void testLogin() throws LoginFailedException, UserAlreadyExistException {
        RegistrationResponse savedUser = walletService.register(registrationRequest);
        assertNotNull(savedUser);
        LoginRequest loginRequest = buildLoginRequest();
        LoginResponse loggedUser = walletService.login(loginRequest);
        assertNotNull(loggedUser);
    }



    @Test
    void testLoginException() throws UserAlreadyExistException {
        RegistrationResponse savedUser = walletService.register(registrationRequest);
        assertNotNull(savedUser);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("Great");
        loginRequest.setPassWord("Fame");
        assertThrows(LoginFailedException.class,()->walletService.login(loginRequest));
    }

    @Test
    void testDeposit() throws InvalidAccountNumber, UserAlreadyExistException {
        RegistrationResponse savedUser = walletService.register(registrationRequest);
        assertNotNull(savedUser);
        DepositRequest depositRequest = buildDepositRequest();
        TransactionResponse savedTransaction = walletService.deposit(depositRequest);
        assertNotNull(savedTransaction);
        System.out.println(savedTransaction);
    }

    @Test
    void testWrongDeposit() throws UserAlreadyExistException, InvalidAccountNumber {
        RegistrationResponse savedUser = walletService.register(registrationRequest);
        assertNotNull(savedUser);
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setDepositAmount(BigDecimal.valueOf(5000));
        depositRequest.setAccountNumber("0212344758");
       assertThrows(InvalidAccountNumber.class, ()->  walletService.deposit(depositRequest));
    }

    @Test
    void testWrongBalance() throws UserAlreadyExistException {
        RegistrationResponse savedUser = walletService.register(registrationRequest);
        assertNotNull(savedUser);
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setDepositAmount(BigDecimal.valueOf(49));
        depositRequest.setAccountNumber("8126188203");
        assertThrows(IllegalArgumentException.class,()->walletService.deposit(depositRequest));
    }

    @Test
    void testTransferMethod() throws InvalidAccountNumber {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferAmount(BigDecimal.valueOf(2000));
        transferRequest.setDepositorAccount("8126188203");
        transferRequest.setReceiverAccountNumber("8140802014");
        walletService.withdrawal(transferRequest);

    }

    private static RegistrationRequest buildWallet(){
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("oladipupoolamilekan2@gmail.com");
        request.setGender("Male");
        request.setPin("1111");
        request.setFirstName("Olamilekan");
        request.setLastName("Oladipupo");
        request.setPassWord("Oladipupo");
        request.setPhoneNumber("08126188203");
        request.setUserName("Great");

        return request;
    }

    private static LoginRequest buildLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("Great");
        loginRequest.setPassWord("Oladipupo");
        return loginRequest;
    }
    private static DepositRequest buildDepositRequest(){
        DepositRequest depositRequest = new DepositRequest();
        BigDecimal amount = BigDecimal.valueOf(5000);
        depositRequest.setDepositAmount(amount);
        depositRequest.setAccountNumber("8126188203");
        return depositRequest;
    }

}