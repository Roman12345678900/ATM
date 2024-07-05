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
        System.out.println("Введите сумму для пополнения (не более 1 000 000):");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > 1000000) {
            System.out.println("Сумма превышает максимальное значение для пополнения.");
        } else {
            account.setBalance(account.getBalance() + amount);
            System.out.println("Баланс успешно пополнен. Текущий баланс: " + account.getBalance());
        }
    }
}