package Senla;

import Senla.Command.Command;
import Senla.Command.Impl.CheckBalanceCommand;
import Senla.Command.Impl.DepositCommand;
import Senla.Command.Impl.TransferFundsCommand;
import Senla.Command.Impl.WithdrawCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATMOperations {
    private Map<String, Account> accounts;
    private DataManager dataManager;
    private Account currentAccount;
    private CardStatus cardStatus;
    private Map<String, Command> commands;

    public ATMOperations() {
        dataManager = new DataManager();
        accounts = dataManager.loadAccounts();
        cardStatus = new CardStatus();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите номер карты (формат XXXX-XXXX-XXXX-XXXX):");
            String cardNumber = scanner.nextLine();

            if (!isValidCardNumber(cardNumber)) {
                System.out.println("Неверный формат номера карты.");
                continue;
            }

            if (cardStatus.isCardBlocked(cardNumber)) {
                System.out.println("Карта заблокирована. Попробуйте позже.");
                continue;
            }

            System.out.println("Введите ПИН-код:");
            String pinCode = scanner.nextLine();

            if (authenticate(cardNumber, pinCode)) {
                System.out.println("Успешная авторизация.");
                cardStatus.resetFailedAttempts(cardNumber);
                initializeCommands(scanner);
                showMenu(scanner);
            } else {
                System.out.println("Неправильный номер карты или ПИН-код.");
                cardStatus.recordFailedAttempt(cardNumber);
            }
        }
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    }

    private boolean authenticate(String cardNumber, String pinCode) {
        Account account = accounts.get(cardNumber);
        if (account != null && account.getPinCode().equals(pinCode)) {
            currentAccount = account;
            return true;
        }
        return false;
    }

    private void initializeCommands(Scanner scanner) {
        commands = new HashMap<>();
        commands.put("1", new CheckBalanceCommand(currentAccount));
        commands.put("2", new WithdrawCommand(currentAccount, scanner));
        commands.put("3", new DepositCommand(currentAccount, scanner));
        commands.put("4", new TransferFundsCommand(currentAccount, accounts, scanner));
    }

    private void showMenu(Scanner scanner) {
        while (true) {
            System.out.println("Выберите действие:");
            System.out.println("1. Проверить баланс");
            System.out.println("2. Снять средства");
            System.out.println("3. Пополнить баланс");
            System.out.println("4. Перевести средства");
            System.out.println("5. Выйти");

            String choice = scanner.nextLine();
            executeOption(choice);
        }
    }

    private void executeOption(String choice) {
        Command command = commands.get(choice);
        if (command != null) {
            command.execute();
        } else if ("5".equals(choice)) {
            dataManager.saveAccounts(accounts);
            System.out.println("Выход из системы.");
            System.exit(0);
        } else {
            System.out.println("Неверный выбор. Повторите попытку.");
        }
    }
}
