import java.io.*;
import java.nio.file.*;

public class MoneyManager {
    private static final String BALANCE_FILE_PATH = "balance.txt";
    private double balance;

    public MoneyManager() {
        readBalanceFromFile();
    }

    private void readBalanceFromFile() {
        try {
            Path path = Paths.get(BALANCE_FILE_PATH);
            if (Files.exists(path)) {
                String content = new String(Files.readAllBytes(path));
                balance = Double.parseDouble(content);
            } else {
                balance = 1000.0; // Start with a default balance
                updateBalanceFile();
            }
        } catch (IOException | NumberFormatException e) {
            balance = 1000.0; // If there's an error, start with default balance
        }
    }

    private void updateBalanceFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(BALANCE_FILE_PATH))) {
            writer.write(String.format("%.2f", balance));
        } catch (IOException e) {
            System.err.println("Error writing balance to file: " + e.getMessage());
        }
    }

    public void updateBalance(double amount) {
        balance += amount;
        updateBalanceFile();
    }

    public double getBalance() {
        return balance;
    }
}
