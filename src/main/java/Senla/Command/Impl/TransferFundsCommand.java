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
        System.out.println("Введите номер карты получателя (формат XXXX-XXXX-XXXX-XXXX):");
        String recipientCardNumber = scanner.nextLine();
        if (!recipientCardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            System.out.println("Неверный формат номера карты.");
            return;
        }
        Account recipientAccount = accounts.get(recipientCardNumber);
        if (recipientAccount == null) {
            System.out.println("Получатель не найден.");
            return;
        }
        System.out.println("Введите сумму для перевода:");
        double amount = Double.parseDouble(scanner.nextLine());
        if (amount > account.getBalance()) {
            System.out.println("Недостаточно средств на счете.");
        } else {
            account.setBalance(account.getBalance() - amount);
            recipientAccount.setBalance(recipientAccount.getBalance() + amount);
            System.out.println("Средства успешно переведены. Текущий баланс: " + account.getBalance());
        }
    }
}
