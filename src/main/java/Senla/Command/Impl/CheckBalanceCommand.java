package Senla.Command.Impl;

import Senla.Account;
import Senla.Command.Command;

public class CheckBalanceCommand implements Command {
    private Account account;

    public CheckBalanceCommand(Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        System.out.println("Ваш баланс: " + account.getBalance());
    }
}
