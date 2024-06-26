package Senla;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CardStatus {
    private static final long UNBLOCK_TIME_MS = 24 * 60 * 60 * 1000;
    private Map<String, Integer> failedAttempts = new HashMap<>();
    private Map<String, Long> blockedCards = new HashMap<>();

    public boolean isCardBlocked(String cardNumber) {
        if (blockedCards.containsKey(cardNumber)) {
            long blockedTime = blockedCards.get(cardNumber);
            if (new Date().getTime() - blockedTime >= UNBLOCK_TIME_MS) {
                unblockCard(cardNumber);
                return false;
            }
            return true;
        }
        return false;
    }

    private void blockCard(String cardNumber) {
        blockedCards.put(cardNumber, new Date().getTime());
    }

    public void recordFailedAttempt(String cardNumber) {
        int attempts = failedAttempts.getOrDefault(cardNumber, 0) + 1;
        failedAttempts.put(cardNumber, attempts);

        if (attempts >= 3) {
            blockCard(cardNumber);
        }
    }

    public void resetFailedAttempts(String cardNumber) {
        failedAttempts.remove(cardNumber);
    }

    private void unblockCard(String cardNumber) {
        blockedCards.remove(cardNumber);
        resetFailedAttempts(cardNumber);
    }
}
