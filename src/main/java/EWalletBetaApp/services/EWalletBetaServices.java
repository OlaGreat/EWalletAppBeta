package EWalletBetaApp.services;

import EWalletBetaApp.data.models.Gender;
import EWalletBetaApp.data.models.Transaction;
import EWalletBetaApp.data.models.Wallet;
import EWalletBetaApp.data.repository.EWalletRepository;
import EWalletBetaApp.dto.request.DepositRequest;
import EWalletBetaApp.dto.request.LoginRequest;
import EWalletBetaApp.dto.request.RegistrationRequest;
import EWalletBetaApp.dto.request.TransactionRequest;
import EWalletBetaApp.dto.response.LoginResponse;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.enums.TransactionStatus;
import EWalletBetaApp.enums.TransactionType;
import EWalletBetaApp.exceptions.InvalidAccountNumber;
import EWalletBetaApp.exceptions.LoginFailedException;
import EWalletBetaApp.exceptions.UserAlreadyExistException;
import EWalletBetaApp.utils.Verify;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class EWalletBetaServices implements WalletService{
    private final EWalletRepository walletRepository;
    private final TransactionService transactionService;

    @Autowired
    public EWalletBetaServices (EWalletRepository eWalletRepository, TransactionService transactionService){
        this.walletRepository = eWalletRepository;
        this.transactionService = transactionService;
    }

    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) throws UserAlreadyExistException {
        verifyUserDetails(registrationRequest);
        registrationRequest.setAccountNumber(generateAccountNumber(registrationRequest.getPhoneNumber()));

        Wallet wallet = new Wallet();
        BeanUtils.copyProperties(registrationRequest,wallet);
        wallet.setGender(Gender.valueOf(registrationRequest.getGender().toUpperCase()));
        wallet.setBalance(BigDecimal.valueOf(0.0));
        Wallet savedWallet = walletRepository.save(wallet);

        RegistrationResponse response = new RegistrationResponse();
        BeanUtils.copyProperties(savedWallet,response);
        response.setMessage("Dear " + savedWallet.getUserName() +", your registration was successful");

        return response;
    }


    @Override
    public TransactionResponse deposit(DepositRequest depositRequest) throws InvalidAccountNumber {
        verifyDepositRequest(depositRequest);
        BigDecimal depositAmount = depositRequest.getDepositAmount();
        String accountNumber = depositRequest.getAccountNumber();
        Optional<Wallet> foundWallet = walletRepository.findByAccountNumber(accountNumber);
        Wallet savedWallet;
        if (foundWallet.isPresent()){
            Wallet wallet = foundWallet.get();
            wallet.setBalance(wallet.getBalance().add(depositAmount));
            savedWallet = walletRepository.save(wallet);
        }else throw new InvalidAccountNumber("The provided account is invalid");

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType(TransactionType.CREDIT);
        transactionRequest.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRequest.setAmount(depositAmount);
        transactionRequest.setWalletId(savedWallet);

        TransactionResponse successfulTransaction = transactionService.createTransaction(transactionRequest);
        return successfulTransaction;
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) throws LoginFailedException {
        String password = loginRequest.getPassWord();
        String userName = loginRequest.getUserName();
        Optional<Wallet> foundWallet = walletRepository.findWalletByUserName(userName);
        return verifyPassword(password, foundWallet);
    }

    private static LoginResponse verifyPassword(String password, Optional<Wallet> foundWallet) throws LoginFailedException {
        if(foundWallet.isPresent()){
            if (foundWallet.get().getPassWord().equals(password)){
                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setMessage("Welcome back "+ foundWallet.get().getUserName());
                return loginResponse;
            }else throw new LoginFailedException("Incorrect username or password");
        }else throw new LoginFailedException("Incorrect username or password");
    }


    private void verifyUserDetails(RegistrationRequest registrationRequest) throws UserAlreadyExistException {
        if(!Verify.verifyEmail(registrationRequest.getEmail())) throw new IllegalArgumentException("invalid email");
        if(isOldUserEmail(registrationRequest.getEmail())) throw new UserAlreadyExistException("Email already exist please login");
        if(isOldUserName(registrationRequest.getUserName())) throw new UserAlreadyExistException("Username already taken please provide a different username");
        if(!isValidNumber(registrationRequest.getPhoneNumber())) throw new IllegalArgumentException("invalid phone number");
        if(isOldUserPhoneNumber(registrationRequest.getPhoneNumber())) throw new UserAlreadyExistException("Phone Number is registered already please login");
    }

    private static void verifyDepositRequest(DepositRequest depositRequest){
        BigDecimal minimumDeposit = BigDecimal.valueOf(50.00);
        if (depositRequest.getDepositAmount().compareTo(minimumDeposit) < 0) throw new IllegalArgumentException("Deposit amount cannot be less than 50");
        if (depositRequest.getAccountNumber().length() != 10) throw new IllegalArgumentException("Account number must be 10 digit");
    }


    private  String generateAccountNumber(String phoneNumber){
        return phoneNumber.substring(1);}

    private  boolean isOldUserPhoneNumber(String phoneNumber){
        Optional<Wallet> foundWallet = walletRepository.findByPhoneNumber(phoneNumber);
        return foundWallet.isPresent();
    }
    private boolean isOldUserName(String userName){
         Optional<Wallet> foundWallet = walletRepository.findWalletByUserName(userName);
        return foundWallet.isPresent();
    }
    private  boolean isOldUserEmail(String email){
        Optional<Wallet> foundWallet = walletRepository.findByEmail(email);
        return foundWallet.isPresent();
    }
    private boolean isValidNumber(String phoneNumber){
        if (phoneNumber.length() != 11) return false;
        return  phoneNumber.chars().allMatch(Character::isDigit);
    }


}
