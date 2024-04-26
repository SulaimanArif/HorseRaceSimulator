import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationMenu extends JFrame {
    private MainController controller;

    public SimulationMenu(MainController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Horse Race Simulation Menu");
        setSize(1920, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set the background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/horsebackground.jpg"));
        setContentPane(backgroundLabel);
        backgroundLabel.setLayout(new BorderLayout());

        // Panel to hold the buttons with BoxLayout and an empty border for padding
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make panel transparent
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10)); // Top, left, bottom, right padding

        JButton btnStartSim = createImageButton("assets/startbutton.png", "Start Simulation");
        JButton btnCustomizeHorses = createImageButton("assets/horsesbutton.png", "Customize Horses");
        JButton btnCustomizeTrack = createImageButton("assets/trackbutton.png", "Customize Track");
        JButton btnViewStats = createImageButton("assets/viewstats.png", "View Stats");

        buttonPanel.add(btnStartSim);
        buttonPanel.add(Box.createVerticalStrut(10)); // Vertical spacer
        buttonPanel.add(btnCustomizeHorses);
        buttonPanel.add(Box.createVerticalStrut(10)); // Vertical spacer
        buttonPanel.add(btnCustomizeTrack);
        buttonPanel.add(Box.createVerticalStrut(10)); // Vertical spacer
        buttonPanel.add(btnViewStats);

        // Wrapper panel using GridBagLayout to center button panel vertically
        JPanel westPanel = new JPanel(new GridBagLayout());
        westPanel.setOpaque(false);
        westPanel.add(buttonPanel); // Center the button panel

        // Add the westPanel to the west of BorderLayout
        backgroundLabel.add(westPanel, BorderLayout.WEST);

        ImageIcon titleIcon = new ImageIcon("assets/title.png");
        JLabel titleLabel = new JLabel(titleIcon, JLabel.CENTER);
        backgroundLabel.add(titleLabel, BorderLayout.NORTH);
    }

    private JButton createImageButton(String imagePath, String actionCommand) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.setActionCommand(actionCommand);
        button.addActionListener(this::buttonActionPerformed);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void buttonActionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Start Simulation":
                controller.showHorseRaceGUI();
                break;
            case "Customize Horses":
                controller.setTrackOrHorse("Horse");
                controller.showImageGallery();
                break;
            case "Customize Track":
                controller.setTrackOrHorse("Track");
                controller.showImageGallery();
                break;
            case "View Stats":
                controller.showStatViewer();
                break;
            default:
                System.out.println("Unknown action");
                break;
        }
    }
}
