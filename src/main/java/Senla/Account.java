package Senla;

public class Account {
    private String cardNumber;
    private String pinCode;
    private double balance;

    public Account(String cardNumber, String pinCode, double balance) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return cardNumber + " " + pinCode + " " + balance;
    }
}

