package Senla.Command.Impl;

import Senla.Account;
import Senla.Command.Command;

import java.util.Scanner;

public class DepositCommand implements Command {
    private Account account;
    private Scanner scanner;

    public DepositCommand(Account account, Scanner scanner) {
        this.account = account;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Enter the amount to top up (no more than 1,000,000):");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > 1000000) {
            System.out.println("The amount exceeds the maximum value for replenishment.");
        } else {
            account.setBalance(account.getBalance() + amount);
            System.out.println("The balance has been successfully replenished. Current balance: " + account.getBalance());
        }
    }
}