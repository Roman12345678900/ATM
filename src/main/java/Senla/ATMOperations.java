package Senla;

import java.util.List;
import java.util.Scanner;
//перевести на карту
class ATMOperations {
    private List<Account> accounts;
    private DataManager dataManager;
    private Account currentAccount;
    private CardStatus cardStatus;

    public ATMOperations() {
        dataManager = new DataManager();
        accounts = dataManager.loadAccounts();
        cardStatus = new CardStatus();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter card number (format XXXX-XXXX-XXXX-XXXX):");
            String cardNumber = scanner.nextLine();

            if (!isValidCardNumber(cardNumber)) {
                System.out.println("Invalid card number format.");
                continue;
            }

            if (cardStatus.isCardBlocked(cardNumber)) {
                System.out.println("The card is blocked. Please try again later.");
                continue;
            }

            System.out.println("Enter your PIN:");
            String pinCode = scanner.nextLine();

            if (authenticate(cardNumber, pinCode)) {
                System.out.println("Successful authorization.");
                cardStatus.resetFailedAttempts(cardNumber);
                showMenu(scanner);
            } else {
                System.out.println("Incorrect card number or PIN.");
                cardStatus.recordFailedAttempt(cardNumber);
            }
        }
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    }

    private boolean authenticate(String cardNumber, String pinCode) {
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber) && account.getPinCode().equals(pinCode)) {
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    private void withdraw(Scanner scanner) {
        System.out.println("Enter the amount to withdraw:");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient funds in the account.");
        } else {
            currentAccount.setBalance(currentAccount.getBalance() - amount);
            System.out.println("The funds were successfully withdrawn. Current balance: " + currentAccount.getBalance());
        }
    }

    private void deposit(Scanner scanner) {
        System.out.println("Enter the amount to top up (no more than 1,000,000):");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > 1000000) {
            System.out.println("The amount exceeds the maximum value for replenishment.");
        } else {
            currentAccount.setBalance(currentAccount.getBalance() + amount);
            System.out.println("The balance has been successfully replenished. Current balance: " + currentAccount.getBalance());
        }
    }

    private void checkBalance() {
        System.out.println("Your balance: " + currentAccount.getBalance());
    }

    private void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("Choose an action:");
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw funds");
            System.out.println("3. Top up balance");
            System.out.println("4. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    withdraw(scanner);
                    break;
                case "3":
                    deposit(scanner);
                    break;
                case "4":
                    dataManager.saveAccounts(accounts);
                    System.out.println("Log out of the system.");
                    return;
                default:
                    System.out.println("Wrong choice. Please try again.");
            }
        }
    }
}
