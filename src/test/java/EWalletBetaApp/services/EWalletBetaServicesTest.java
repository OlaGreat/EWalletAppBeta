package EWalletBetaApp.services;

import EWalletBetaApp.dto.request.DepositRequest;
import EWalletBetaApp.dto.request.LoginRequest;
import EWalletBetaApp.dto.request.RegistrationRequest;
import EWalletBetaApp.dto.request.TransferRequest;
import EWalletBetaApp.dto.response.LoginResponse;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql (scripts = {"/db/insert.sql"})
class EWalletBetaServicesTest {
    @Autowired
    WalletService walletService;




    @Test
    void testRegisterNewWallet() throws UserAlreadyExistException {
            RegistrationRequest request = new RegistrationRequest();
            request.setEmail("test@gmail.com");
            request.setGender("Male");
            request.setPin("1111");
            request.setFirstName("Olamilekan");
            request.setLastName("Oladipupo");
            request.setPassWord("Oladipupo");
            request.setPhoneNumber("08127188203");
            request.setUserName("Great55");


        RegistrationResponse savedUser = walletService.register(request);
        assertNotNull(savedUser);
        System.out.println(savedUser);

    }

    @Test
    void testLogin() throws LoginFailedException, UserAlreadyExistException {
        LoginRequest loginRequest = buildLoginRequest();
        LoginResponse loggedUser = walletService.login(loginRequest);
        assertNotNull(loggedUser);
    }


    @Test
    void testLoginException() throws UserAlreadyExistException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("Great");
        loginRequest.setPassWord("Fame");
        assertThrows(LoginFailedException.class,()->walletService.login(loginRequest));
    }

    @Test
    void testDeposit() throws InvalidAccountNumber{
        DepositRequest depositRequest = buildDepositRequest();
        TransactionResponse savedTransaction = walletService.deposit(depositRequest);
        assertNotNull(savedTransaction);
        System.out.println(savedTransaction);
    }

    @Test
    void testWrongDeposit(){
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setDepositAmount(BigDecimal.valueOf(5000));
        depositRequest.setAccountNumber("0212344758");
       assertThrows(InvalidAccountNumber.class, ()->  walletService.deposit(depositRequest));
    }


    @Test
    void testWrongAmount(){
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setDepositAmount(BigDecimal.valueOf(49));
        depositRequest.setAccountNumber("8126188203");
        assertThrows(IllegalArgumentException.class,()->walletService.deposit(depositRequest));
    }

    @Test
    void testTransferMethod() throws EWalletBetaException {
        DepositRequest depositRequest = buildDepositRequest();
        TransactionResponse savedTransaction = walletService.deposit(depositRequest);
        assertNotNull(savedTransaction);

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferAmount(BigDecimal.valueOf(3000));
        transferRequest.setDepositorAccount("8126188203");
        transferRequest.setReceiverAccountNumber("8140802014");
        transferRequest.setPin("1111");

        TransactionResponse transactionReceipt = walletService.withdrawal(transferRequest);
        log.info(transactionReceipt.toString());
        assertNotNull(savedTransaction);

    }

    @Test
    void testTransferMethodThrowsInsufficientBalance() throws EWalletBetaException {
        DepositRequest depositRequest = buildDepositRequest();
        TransactionResponse savedTransaction = walletService.deposit(depositRequest);
        assertNotNull(savedTransaction);

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferAmount(BigDecimal.valueOf(6000));
        transferRequest.setDepositorAccount("8126188203");
        transferRequest.setReceiverAccountNumber("8140802014");
        transferRequest.setPin("1111");


        assertThrows(InsufficientBalance.class, ()->walletService.withdrawal(transferRequest));

    }



    private static LoginRequest buildLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName("Great1");
        loginRequest.setPassWord("great");
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