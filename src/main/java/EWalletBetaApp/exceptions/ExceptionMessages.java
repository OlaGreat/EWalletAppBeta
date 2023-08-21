package EWalletBetaApp.exceptions;

public enum ExceptionMessages {
    INSUFFICIENT_BALANCE("Transfer amount cannot be greater than balance "),
    INCORRECT_PIN("Incorrect pin"),
    INVALID_ACCOUNT_NUMBER("Incorrect Account");

    ExceptionMessages(String message) {this.message = message;}
    public final String message;

    public String getMessage() {
        return this.message;
    }
}
