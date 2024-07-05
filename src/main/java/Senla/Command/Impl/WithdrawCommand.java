package Senla.Command.Impl;

import Senla.Account;
import Senla.Command.Command;

import java.util.Scanner;

public class WithdrawCommand implements Command {
    private Account account;
    private Scanner scanner;

    public WithdrawCommand(Account account, Scanner scanner) {
        this.account = account;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the amount to withdraw:");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > account.getBalance()) {
            System.out.println("Insufficient funds in the account.");
        } else {
            account.setBalance(account.getBalance() - amount);
            System.out.println("The funds were successfully withdrawn. Current balance: " + account.getBalance());
        }
    }
}
