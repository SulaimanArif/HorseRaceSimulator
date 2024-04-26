import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RaceStatsViewer extends JFrame {
    private Map<String, String> raceData = new HashMap<>();
    private MainController controller;

    public RaceStatsViewer(MainController controller) {
        super("Race Statistics Viewer");
        this.controller = controller;  // Ensure controller is assigned
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setLocationRelativeTo(null);

        BackgroundPanel mainPanel = new BackgroundPanel("assets/backgroundstats.png");
        mainPanel.setLayout(new BorderLayout());

        try {
            loadRaceData("race_history.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load race data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon buttonIcon = new ImageIcon("assets/mainmenu.png");
        JButton customButton = new JButton(buttonIcon);
        customButton.addActionListener(e -> controller.showSimulationMenu());
        customButton.setBorderPainted(false);
        customButton.setFocusPainted(false);
        customButton.setContentAreaFilled(false);

        ImageIcon titleIcon = new ImageIcon("assets/statstitle.png");
        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setVerticalAlignment(JLabel.CENTER);

        topPanel.add(customButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        topPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        List<String> sortedDates = raceData.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        sortedDates.forEach(date -> {
            JButton button = new JButton(date);
            button.addActionListener(e -> {
                JOptionPane.showMessageDialog(RaceStatsViewer.this, raceData.get(date), "Statistics for " + date, JOptionPane.INFORMATION_MESSAGE);
            });
            buttonPanel.add(button, gbc);
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void loadRaceData(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder currentStats = new StringBuilder();
        String currentDate = null;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("-------------------------")) {
                if (currentDate != null && currentStats.length() > 0) {
                    raceData.put(currentDate, currentStats.toString().trim());
                    currentStats = new StringBuilder();
                }
            } else if (line.contains("- Race Duration:")) {
                int index = line.indexOf(" -");
                currentDate = line.substring(0, index).trim();
            } else if (!line.trim().isEmpty()) {
                currentStats.append(line).append("\n");
            }
        }
        if (currentDate != null && currentStats.length() > 0) {
            raceData.put(currentDate, currentStats.toString().trim());
        }
        reader.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        });
    }
}
