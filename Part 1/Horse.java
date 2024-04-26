
import pak.MathUtils;

/**
 * Write a description of class Horse here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Horse
{
    //Fields of class Horse
    
    private String horseName;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean fallen;
    private double horseConfidence;
    private double originalHorseConfidence;
    private int streak;
      
    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {

       this.horseSymbol = horseSymbol;
       this.horseName = horseName;
       this.horseConfidence = horseConfidence;
       this.fallen = false;
       this.distanceTravelled = 0;
       this.streak = 0;
       this.originalHorseConfidence = horseConfidence;
    }
    
    
    
    //Other methods of class Horse
    public void increaseStreak(){
        streak++;
        if(horseConfidence<1){
            horseConfidence += 0.1;
            horseConfidence = MathUtils.roundToNearestTenth(horseConfidence);
        }
    }

    public void resetStreak(){
        streak = 0;
        horseConfidence = originalHorseConfidence;
    }

    public void fall()
    {
        this.fallen = true;
    }
    
    public double getConfidence()
    {
        return horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return distanceTravelled;
    }
    
    public String getName()
    {
        return horseName;
    }
    
    public char getSymbol()
    {
        return horseSymbol;
    }
    
    public void goBackToStart()
    {
        distanceTravelled = 0;
    }
    
    public boolean hasFallen()
    {
        if (fallen == true){
            return true;
        }else{
            return false;
        }
    }

    public void moveForward()
    {
        distanceTravelled++;
    }

    public void setConfidence(double newConfidence)
    {
        horseConfidence = newConfidence;
    }
    
    public void setSymbol(char newSymbol)
    {
        horseSymbol = newSymbol;;
    }
    
}
