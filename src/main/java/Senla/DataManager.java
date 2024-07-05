package Senla;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private static final String DATA_FILE = "accounts.txt";

    public Map<String, Account> loadAccounts() {
        Map<String, Account> accounts = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String cardNumber = parts[0];
                    String pinCode = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.put(cardNumber, new Account(cardNumber, pinCode, balance));
                }
            }
        } catch (IOException e) {
            System.out.println("Error when enabling data: " + e.getMessage());
        }

        return accounts;
    }

    public void saveAccounts(Map<String, Account> accounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Account account : accounts.values()) {
                writer.write(account.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
}
