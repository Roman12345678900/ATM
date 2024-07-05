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
        System.out.println("Введите сумму для снятия:");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > account.getBalance()) {
            System.out.println("Недостаточно средств на счете.");
        } else {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Средства успешно сняты. Текущий баланс: " + account.getBalance());
        }
    }
}
