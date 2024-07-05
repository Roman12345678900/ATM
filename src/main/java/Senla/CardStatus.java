package Senla;

import java.util.HashMap;
import java.util.Map;

public class CardStatus {
    private Map<String, Integer> failedAttempts = new HashMap<>();
    private Map<String, Long> blockedCards = new HashMap<>();
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long BLOCK_DURATION_MS = 24 * 60 * 60 * 1000; // 24 часа

    public void recordFailedAttempt(String cardNumber) {
        int attempts = failedAttempts.getOrDefault(cardNumber, 0);
        attempts++;
        failedAttempts.put(cardNumber, attempts);
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            blockedCards.put(cardNumber, System.currentTimeMillis());
            System.out.println("Карта заблокирована из-за превышения количества неудачных попыток ввода ПИН-кода.");
        }
    }

    public void resetFailedAttempts(String cardNumber) {
        failedAttempts.remove(cardNumber);
    }

    public boolean isCardBlocked(String cardNumber) {
        Long blockedTime = blockedCards.get(cardNumber);
        if (blockedTime == null) {
            return false;
        }
        if (System.currentTimeMillis() - blockedTime >= BLOCK_DURATION_MS) {
            blockedCards.remove(cardNumber);
            return false;
        }
        return true;
    }
}
