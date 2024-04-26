import java.awt.*;
import javax.swing.*;

class BettingDialog extends JDialog {
    private JTextField[] betFields;
    private JButton placeBetsButton;
    private boolean betsPlaced = false;
    private MoneyManager moneyManager;

    public BettingDialog(JFrame parent, int horseCount, Race race, MoneyManager moneyManager) {
        super(parent, "Place Your Bets", true);
        setSize(700, 400); // Increased size to better accommodate layout changes
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout()); // Use BorderLayout for overall structure

        // Create and add a panel for the title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JLabel("Place Your Bets"));
        add(titlePanel, BorderLayout.NORTH);

        // Main betting panel with grid layout for horses and bets
        JPanel bettingPanel = new JPanel(new GridLayout(horseCount + 1, 2)); // Adjusted for two columns
        JLabel balanceLabel = new JLabel("Current Balance: $" + moneyManager.getBalance(), JLabel.CENTER);
        bettingPanel.add(balanceLabel);
        bettingPanel.add(new JLabel("")); // Filler to maintain grid

        this.moneyManager = moneyManager;
        betFields = new JTextField[horseCount];
        for (int i = 0; i < horseCount; i++) {
            Horse horse = race.getHorse(i + 1);
            String labelWithOdds = String.format("Bet on Horse %d: (Odds: %.2f)", i + 1, horse.getOdds());

            bettingPanel.add(new JLabel(labelWithOdds));
            betFields[i] = new JTextField(10);
            bettingPanel.add(betFields[i]);
        }

        // Adding the betting panel to the center of the BorderLayout
        add(bettingPanel, BorderLayout.CENTER);

        // Panel for the place bets button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        placeBetsButton = new JButton("Place Bets");
        placeBetsButton.addActionListener(e -> {
            try {
                double totalBetAmount = 0;
                for (int i = 0; i < horseCount; i++) {
                    totalBetAmount += Double.parseDouble(betFields[i].getText());
                    Horse horse = race.getHorse(i + 1);
                    horse.addToTotalBets(Double.parseDouble(betFields[i].getText()));
                }
                if (totalBetAmount > moneyManager.getBalance()) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                } else {
                    moneyManager.updateBalance(-totalBetAmount); 
                    betsPlaced = true;
                    JOptionPane.showMessageDialog(this, "Bets placed successfully!");
                    setVisible(false);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for bets.");
            }
        });
        buttonPanel.add(placeBetsButton);

        // Adding the button panel to the south of the BorderLayout
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean areBetsPlaced() {
        return betsPlaced;
    }
}
