package Senla;

import java.util.HashMap;
import java.util.Map;

public class CardStatus {
    private Map<String, Integer> failedAttempts = new HashMap<>();
    private Map<String, Long> blockedCards = new HashMap<>();
    private static final int maxFailedAttempts = 3;
    private static final long blockDurationMs = 24 * 60 * 60 * 1000;

    public void recordFailedAttempt(String cardNumber) {
        int attempts = failedAttempts.getOrDefault(cardNumber, 0);
        attempts++;
        failedAttempts.put(cardNumber, attempts);
        if (attempts >= maxFailedAttempts) {
            blockedCards.put(cardNumber, System.currentTimeMillis());
            System.out.println("The card is blocked due to the number of unsuccessful attempts to enter the PIN code..");
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
        if (System.currentTimeMillis() - blockedTime >= blockDurationMs) {
            blockedCards.remove(cardNumber);
            return false;
        }
        return true;
    }
}
