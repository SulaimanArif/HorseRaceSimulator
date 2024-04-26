import javax.swing.*;
import java.util.*;

public class MainController implements ControllerInterface {

    private JFrame currentFrame;

    //receive and send data methods
    public String trackOrHorse; 
    public String trackData = null;

    @Override
    public void setHorseData(String name, String assetPath){
        horseData.add(name);
        horseData.add(assetPath);
    }
    public List<Object> getHorseData(){
        return horseData;
    }

    public String getTrackOrHorse(){
        return trackOrHorse;
    }

    public void setTrackOrHorse(String trackHorse){
        trackOrHorse = trackHorse;
    }

    public String getTrackData(){
        return trackData;
    }

    public void setTrackData(String trackFilePath){
        trackData = trackFilePath;
    }

    //end of data methods

    //window controller methods

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainController().startApp());
    }

    private void startApp() {
        showSimulationMenu();
    }

    public void showSimulationMenu() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new SimulationMenu(this);
        currentFrame.setVisible(true);
    }

    public void showImageGallery() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new ImageGallery(this);
        currentFrame.setVisible(true);
    }

    public void disposeFrame(){
        currentFrame.dispose();
    }

    public void showHorseRaceGUI() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new HorseRaceGUI(this);
        currentFrame.setVisible(true);
    }

    public void showStatViewer() {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = new RaceStatsViewer(this);
        currentFrame.setVisible(true);
    }
}
