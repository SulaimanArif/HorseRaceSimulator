import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class ImageGallery extends JFrame implements ActionListener {
    private JLabel imageLabel;
    private List<String> imagePaths;
    private int currentIndex = 0;
    private JTextField horseNameField;
    private MainController controller;
    private String backgroundImagePath = "assets/customisebackground.png"; // Adjust the path as necessary


    public ImageGallery(MainController controller) {
        this.controller = controller;
        setTitle("Image Gallery");
        setSize(1920, 1080);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        // Create a panel that will draw the background
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon backgroundIcon = new ImageIcon(backgroundImagePath);
                g.drawImage(backgroundIcon.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        if(controller.getTrackOrHorse() == "Horse"){
            imagePaths = Arrays.asList("assets/horse1.png", "assets/horse2.png",  "assets/horse3.png", "assets/horse4.png", "assets/horse5.png" );
        }else{
            imagePaths = Arrays.asList("assets/backgroundgrass.png", "assets/backgroundstone.png", "assets/backgroundwater.png");
        }
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        updateImage();
    
        JButton prevButton = createImageButton("assets/leftarrow.png", this);
        JButton nextButton = createImageButton("assets/rightarrow.png", this);
        prevButton.setActionCommand("Previous");
        nextButton.setActionCommand("Next");
    
        backgroundPanel.add(prevButton, BorderLayout.WEST);
        backgroundPanel.add(nextButton, BorderLayout.EAST);
        backgroundPanel.add(imageLabel, BorderLayout.CENTER);
        
        if(controller.getTrackOrHorse() == "Horse"){
            setupBottomPanel(backgroundPanel);
        }

        setupTopPanel(backgroundPanel);
    
        setContentPane(backgroundPanel);
    }

    private JButton createImageButton(String imagePath, ActionListener action) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.addActionListener(action);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    private void setupBottomPanel(JPanel panel) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        bottomPanel.setOpaque(true); // Make panel transparent
        bottomPanel.setBackground(Color.GREEN);
        bottomPanel.add(new JLabel("Horse Name:"));
    
        horseNameField = new JTextField(20);
        horseNameField.setBorder(BorderFactory.createCompoundBorder(
            horseNameField.getBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    
        bottomPanel.add(horseNameField);
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }
    

    private void setupTopPanel(JPanel panel) {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(true); // Make panel transparent
        topPanel.setBackground(Color.GREEN);
        String submitButtonImagePath = "assets/submitbutton.png"; // Path to submit button image
        JButton submitButton = createImageButton(submitButtonImagePath, e -> validateAndSubmit());
        topPanel.add(submitButton, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);
    }

    private void validateAndSubmit() {
        if(controller.getTrackOrHorse() == "Horse"){
            String text = horseNameField.getText();
            if (text.matches("[a-zA-Z ]+")) {
                controller.setHorseData(text, imagePaths.get(currentIndex)); // Assuming setHorseData exists
                controller.showSimulationMenu();
            } else {
                horseNameField.setText("");
                JOptionPane.showMessageDialog(this, "Please enter only letters and spaces.");
            }
        }else{
            controller.setTrackData(imagePaths.get(currentIndex));
            controller.showSimulationMenu();
        }
    }

    private void updateImage() {
        ImageIcon icon = new ImageIcon(imagePaths.get(currentIndex));
        imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(760, 540, Image.SCALE_SMOOTH)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        currentIndex = "Next".equals(e.getActionCommand()) ? (currentIndex + 1) % imagePaths.size() : (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
        updateImage();
    }
}
