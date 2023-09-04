package EWalletBetaApp.services;

import EWalletBetaApp.data.models.Gender;
import EWalletBetaApp.data.models.Wallet;
import EWalletBetaApp.data.repository.EWalletRepository;
import EWalletBetaApp.dto.request.*;
import EWalletBetaApp.dto.response.LoginResponse;
import EWalletBetaApp.dto.response.RegistrationResponse;
import EWalletBetaApp.dto.response.TransactionResponse;
import EWalletBetaApp.exceptions.*;
import EWalletBetaApp.utils.Verify;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static EWalletBetaApp.enums.TransactionStatus.SUCCESSFUL;
import static EWalletBetaApp.enums.TransactionType.CREDIT;
import static EWalletBetaApp.enums.TransactionType.DEBIT;
import static EWalletBetaApp.exceptions.ExceptionMessages.*;

@Service
@AllArgsConstructor
@Slf4j
public class EWalletBetaServices implements WalletService{
    private final EWalletRepository walletRepository;
    private final TransactionService transactionService;


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
        transactionRequest.setTransactionType(CREDIT);
        transactionRequest.setTransactionStatus(SUCCESSFUL);
        transactionRequest.setAmount(depositAmount);
        transactionRequest.setWalletId(savedWallet);

        TransactionResponse successfulTransaction = transactionService.createTransaction(transactionRequest);
        return successfulTransaction;
    }

    private static String nairaFormatter(BigDecimal amount) {
        Locale nigeria = new Locale("en", "NG");
        NumberFormat nigeriaCurrency = NumberFormat.getCurrencyInstance(nigeria);
        return nigeriaCurrency.format(amount);
    }

    @Override
    public TransactionResponse withdrawal(TransferRequest transferRequest) throws EWalletBetaException {
        verifyWithdrawalRequest(transferRequest);
        BigDecimal transferAmount = transferRequest.getTransferAmount();
        String receiverAccountNumber = transferRequest.getReceiverAccountNumber();
        String senderAccountNumber = transferRequest.getDepositorAccount();
        String senderPin = transferRequest.getPin();

        Wallet senderWallet = walletRepository.findByAccountNumber(senderAccountNumber)
                                              .orElseThrow(()->new InvalidAccountNumber(INVALID_ACCOUNT_NUMBER.getMessage()));

        Wallet receiverWallet = walletRepository.findByAccountNumber(receiverAccountNumber)
                                                .orElseThrow(()-> new  InvalidAccountNumber(INVALID_ACCOUNT_NUMBER.getMessage()));

        if (senderWallet.getBalance().compareTo(transferAmount) < 0) throw new InsufficientBalance(INSUFFICIENT_BALANCE.getMessage());
        if(!Objects.equals(senderPin, senderWallet.getPin())) throw new IncorrectPin(INCORRECT_PIN.getMessage());

        BigDecimal senderNewBalance = senderWallet.getBalance().subtract(transferAmount);
        senderWallet.setBalance(senderNewBalance);
        Wallet savedWallet = walletRepository.save(senderWallet);

        BigDecimal receiverNewBalance = receiverWallet.getBalance().add(transferAmount);
        receiverWallet.setBalance(receiverNewBalance);
        walletRepository.save(receiverWallet);


        TransactionResponse successfulTransaction = transactionReceipt(transferAmount, senderWallet, receiverWallet);

        return successfulTransaction;
    }

    @Override
    public String checkBalance(String pin, Long id) {
        Optional<Wallet> foundWallet = walletRepository.findById(id);
        Wallet wallet = foundWallet.get();
        if(wallet.getPin().equals(pin)){
            Locale locale = new Locale("en", "NG");
            NumberFormat nigeriaCurrency = NumberFormat.getCurrencyInstance(locale);
            return nigeriaCurrency.format(wallet.getBalance());
        }




        return null;
    }

    private TransactionResponse transactionReceipt(BigDecimal transferAmount, Wallet senderWallet, Wallet receiverWallet) {

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setTransactionType(DEBIT);
        transactionRequest.setTransactionStatus(SUCCESSFUL);
        transactionRequest.setAmount(transferAmount);
        transactionRequest.setWalletId(senderWallet);

        TransactionResponse successfulTransaction =   transactionService.createTransaction(transactionRequest);
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

    private static void verifyWithdrawalRequest(TransferRequest transferRequest) throws InvalidAccountNumber {
        if (transferRequest.getTransferAmount().compareTo(BigDecimal.valueOf(50)) < 0) throw new IllegalArgumentException("Transfer amount cannot be less than 50");
        if (transferRequest.getReceiverAccountNumber().length() != 10) throw new InvalidAccountNumber("The provided account is invalid");
        if (transferRequest.getDepositorAccount().length() != 10) throw new InvalidAccountNumber("The provided account is invalid");
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
