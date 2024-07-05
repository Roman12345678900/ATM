package Senla.Command.Impl;

import Senla.Account;
import Senla.Command.Command;

import java.util.Map;
import java.util.Scanner;

public class TransferFundsCommand implements Command {
    private Account account;
    private Map<String, Account> accounts;
    private Scanner scanner;

    public TransferFundsCommand(Account account, Map<String, Account> accounts, Scanner scanner) {
        this.account = account;
        this.accounts = accounts;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the recipient's card number (format XXXX-XXXX-XXXX-XXXX):");
        String recipientCardNumber = scanner.nextLine();
        if (!recipientCardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            System.out.println("Invalid card number format.");
            return;
        }
        Account recipientAccount = accounts.get(recipientCardNumber);
        if (recipientAccount == null) {
            System.out.println("Recipient not found.");
            return;
        }
        System.out.println("Enter the amount to transfer:");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > account.getBalance()) {
            System.out.println("Insufficient funds in the account.");
        } else {
            account.setBalance(account.getBalance() - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);
            System.out.println("Funds have been successfully transferred. Current balance: " + account.getBalance());
        }
    }
}
