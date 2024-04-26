import pak.MathUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Horse class represents an individual competitor in a horse race, with capabilities to handle betting.
 */
public class Horse {
    // Fields of class Horse
    private String horseName;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean fallen;
    private double horseConfidence;
    private double originalHorseConfidence;
    private int streak;
    private String horseAsset;
    private List<Double> confidenceHistory = new ArrayList<>();

    // Betting-related fields
    private double odds;          // Betting odds based on horse confidence
    private double totalBets;     // Total monetary bets placed on this horse

    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.fallen = false;
        this.distanceTravelled = 0;
        this.streak = 0;
        this.originalHorseConfidence = horseConfidence;
        this.totalBets = 0; // Initialize total bets to zero
    }

    // Getter and Setter for the odds
    public double getOdds() {
        return odds;
    }

    public void setOdds(double odds) {
        this.odds = odds;
    }

    // Getter and Setter for total bets
    public double getTotalBets() {
        return totalBets;
    }

    public void addToTotalBets(double amount) {
        this.totalBets += amount;
    }


    public void setHorseAsset(String filepath) {
        horseAsset = filepath;
    }

    public String getHorseAsset() {
        return horseAsset;
    }

    public void increaseStreak() {
        streak++;
        if (horseConfidence < 1) {
            horseConfidence += 0.1;
            horseConfidence = MathUtils.roundToNearestTenth(horseConfidence);
        }
        confidenceHistory.add(horseConfidence);
    }

    public void resetStreak() {
        streak = 0;
        horseConfidence = originalHorseConfidence;
        confidenceHistory.add(horseConfidence);
    }

    public void fall() {
        this.fallen = true;
    }

    public double getConfidence() {
        return horseConfidence;
    }

    public int getDistanceTravelled() {
        return distanceTravelled;
    }

    public String getName() {
        return horseName;
    }

    public char getSymbol() {
        return horseSymbol;
    }

    public void goBackToStart() {
        distanceTravelled = 0;
    }

    public boolean hasFallen() {
        return fallen;
    }

    public void moveForward() {
        if (!fallen) {
            distanceTravelled++;
        }
    }

    public void setConfidence(double newConfidence) {
        horseConfidence = newConfidence;
        confidenceHistory.add(newConfidence);
    }

    public double calculateAverageConfidence() {
        double total = 0.0;
        for (double conf : confidenceHistory) {
            total += conf;
        }
        return total / confidenceHistory.size();
    }
}
