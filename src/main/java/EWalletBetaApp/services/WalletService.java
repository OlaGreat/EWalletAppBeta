package EWalletBetaApp.services;

import EWalletBetaApp.dto.request.DepositRequest;
import EWalletBetaApp.dto.request.LoginRequest;
import EWalletBetaApp.dto.request.RegistrationRequest;
import EWalletBetaApp.dto.request.TransferRequest;
import EWalletBetaApp.dto.response.LoginResponse;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.exceptions.EWalletBetaException;
import EWalletBetaApp.exceptions.InvalidAccountNumber;
import EWalletBetaApp.exceptions.LoginFailedException;
import EWalletBetaApp.exceptions.UserAlreadyExistException;

public interface WalletService {
    RegistrationResponse register(RegistrationRequest registrationRequest) throws UserAlreadyExistException;

    LoginResponse login(LoginRequest loginRequest) throws LoginFailedException;

    TransactionResponse deposit(DepositRequest depositRequest) throws InvalidAccountNumber;

    TransactionResponse withdrawal(TransferRequest withdrawalRequest) throws EWalletBetaException;

}
