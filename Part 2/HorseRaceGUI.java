import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

import pak.MathUtils;

public class HorseRaceGUI extends JFrame {
    private static MainController controller;
    private JPanel trackPanel;
    private JLabel[] horseLabels;
    private JLabel[] horseStatusLabels;
    private JLabel[] horseInfoLabels;
    private MoneyManager moneyManager;
    private Timer timer;
    private Race race;
    private ImageIcon horseIcon;
    private ImageIcon opponentIcon1;
    private ImageIcon opponentIcon2;
    private int fallenCount = 0;
    private long startTime;
    private static final int PANEL_WIDTH = 1920;
    private static final int PANEL_HEIGHT = 1080;
    private static final int HORSE_COUNT = 3; 

    public HorseRaceGUI(MainController controller) {
        this.controller = controller;
        this.moneyManager = new MoneyManager();
        setTitle("Horse Race Simulation");
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        displayBettingDialog();
        
    }

    private void initializeComponents() {
        if(controller.getTrackData() == null){
            trackPanel = new BackgroundPanel("assets/backgroundgrass.png");
        }else{
            trackPanel = new BackgroundPanel(controller.getTrackData()); 
        }
        trackPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        trackPanel.setLayout(null);
        trackPanel.setOpaque(false); 
    
        setupInfoPanel(); 
        setupBottomPanel();
    
        horseLabels = new JLabel[HORSE_COUNT];
        List<ImageIcon> imagelist = new ArrayList<>();
        try {
            horseIcon = new ImageIcon((String)controller.getHorseData().get(1)); // Make sure this path is correct
            opponentIcon1 = new ImageIcon("assets/opphorse1.png");
            opponentIcon2 = new ImageIcon("assets/opphorse2.png");
            imagelist.add(opponentIcon1);
            imagelist.add(opponentIcon2);
            imagelist.add(horseIcon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please make sure you've configured your horse");
            controller.setTrackOrHorse("Horse");
            controller.showImageGallery();
            return;
        }
        for (int i = 0; i < HORSE_COUNT; i++) {
            Image scaledImage = imagelist.get(i).getImage().getScaledInstance(150, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            horseLabels[i] = new JLabel(scaledIcon);
            horseLabels[i].setBounds(0, 150 * (i + 1), scaledIcon.getIconWidth(), scaledIcon.getIconHeight());
            trackPanel.add(horseLabels[i]);
        }

        race = new Race(15); 
        // Adding horses
        double min = 0.3;
        double max = 0.6;
        for (int i = 0; i < HORSE_COUNT - 1; i++) {
            double randomConfidence = min + Math.random() * (max - min);
            double roundedConfidence = MathUtils.roundToNearestTenth(randomConfidence);
            Horse horse = new Horse('H', "Opponent " + (i + 1), roundedConfidence);
            race.addHorse(horse, i + 1);
        }
        double randomConfidence = min + Math.random() * (max - min);
        double roundedConfidence = MathUtils.roundToNearestTenth(randomConfidence);
        Horse horse = new Horse('0', (String)controller.getHorseData().get(0), roundedConfidence);
        race.addHorse(horse, 3);

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                race.advanceRace();
                updateHorsePositions();
                checkFallen();
                if (race.isFinished()) {
                    timer.stop();
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    race.logResults(duration); // Call to log the results
                    handlePayouts();
                    JOptionPane.showMessageDialog(HorseRaceGUI.this, "Race Finished!");
                    controller.showSimulationMenu();
                }
            }
        });

        add(trackPanel);
        
    }

    private void checkFallen(){
        fallenCount = 0;
        for(int f = 0; f<3; f++){
            Horse checkhorse = race.getHorse(f + 1);
            if(checkhorse.hasFallen() == true){
                fallenCount++;
            }
            if(fallenCount == 3){
                timer.stop();
                race.setFallenStatus(true);
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                race.logResults(duration);
                JOptionPane.showMessageDialog(HorseRaceGUI.this, "All Horses Have Fallen!");
                controller.showSimulationMenu();
            }
        }
    }

    private void setupInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(HORSE_COUNT, 1));
        infoPanel.setBackground(Color.green);
        infoPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50)); // Smaller height
    
        horseInfoLabels = new JLabel[HORSE_COUNT];
    
        for (int i = 0; i < HORSE_COUNT; i++) {
            horseInfoLabels[i] = new JLabel("Horse " + (i + 1) + ": Waiting to start...");
            horseInfoLabels[i].setFont(new Font("Serif", Font.BOLD, 16));
            infoPanel.add(horseInfoLabels[i]);
        }
    
        getContentPane().add(infoPanel, BorderLayout.NORTH);
    }

    private void setupBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(HORSE_COUNT, 1));
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 50)); // Smaller height
    
        horseStatusLabels = new JLabel[HORSE_COUNT];
    
        for (int i = 0; i < HORSE_COUNT; i++) {
            horseStatusLabels[i] = new JLabel();
            horseStatusLabels[i].setFont(new Font("Serif", Font.BOLD, 16));
            horseInfoLabels[i].setHorizontalAlignment(JLabel.CENTER);
            bottomPanel.add(horseStatusLabels[i]);
        }
    
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }    

    private void updateHorsePositions() {
        for (int i = 0; i < HORSE_COUNT; i++) {
            Horse horse = race.getHorse(i + 1);
            int xPosition = (int) ((double) horse.getDistanceTravelled() / race.getRaceLength() * (PANEL_WIDTH - horseLabels[i].getWidth()));
            horseLabels[i].setLocation(xPosition, horseLabels[i].getY());
    
            // Update the information and status panels
            horseStatusLabels[i].setHorizontalAlignment(JLabel.CENTER);
            horseInfoLabels[i].setText("Horse " + horse.getName() + " - Distance: " + horse.getDistanceTravelled() + "m");
            String status = horse.hasFallen() ? "Fallen" : "Running";
            horseStatusLabels[i].setText("Confidence: " + String.format("%.2f", horse.getConfidence()) + ", Status: " + status);
        }
        trackPanel.repaint();
    }

    private void displayBettingDialog() {
        calculateOdds();
        BettingDialog bettingDialog = new BettingDialog(this, HORSE_COUNT, race, moneyManager);
        bettingDialog.setVisible(true);
        if (bettingDialog.areBetsPlaced()) {
            startRace();
        } else {
            JOptionPane.showMessageDialog(this, "Race cannot start without placing bets.");
        }
    }

    public void startRace() {
        this.startTime = System.currentTimeMillis();
        System.out.println("test");
        timer.start();
    }

    private void calculateOdds() {
        double totalConfidence = 0;
        for (int i = 1; i <= HORSE_COUNT; i++) {
            totalConfidence += race.getHorse(i).getConfidence();
        }
        for (int i = 1; i <= HORSE_COUNT; i++) {
            Horse horse = race.getHorse(i);
            double odds = totalConfidence / horse.getConfidence();
            horse.setOdds(odds);
        }
    }

    private void handlePayouts() {
        Horse winningHorse = race.getWinningHorse();
        if (winningHorse != null) {
            double winPayout = winningHorse.getTotalBets() * winningHorse.getOdds() + winningHorse.getTotalBets();
            moneyManager.updateBalance(winPayout); // Add winnings to balance
            double totalLoss = 0;
    
            // Deduct the bets placed on the losing horses
            for (int i = 1; i <= HORSE_COUNT; i++) {
                Horse horse = race.getHorse(i);
                if (horse != winningHorse) {
                    totalLoss += horse.getTotalBets();
                }
            }
    
            JOptionPane.showMessageDialog(this, "Horse " + winningHorse.getName() + " won! Total payout: $" + MathUtils.roundToNearestTenth(winPayout - totalLoss - winningHorse.getTotalBets()));
        } else {
            JOptionPane.showMessageDialog(this, "No horse won the race.");
        }
    }
        

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            
        });
    }
}
