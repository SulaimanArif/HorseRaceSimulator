import java.util.*;

public class gameMenu {
    
    private static int trackLength = 5;
    private static boolean horseInitialised = false;
    private static Horse[] horselist = new Horse[3];

    public static Horse[] horseInitialisation(){
        Horse[] horses = new Horse[3];
        Scanner scanner = new Scanner(System.in);
        char symbolChar=' ';

        for(int x = 0; x<3; x++){

            String name;
            while (true) {
                System.out.print("What would you like to name the Horse? ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z ]+")) { // Matches only letters and spaces
                    break; // Exit the loop if the name is valid
                } else {
                    System.out.println("Invalid input. Please use only letters and spaces.");
                }
            }

            double confidenceNum = 0; // Declare variable to store the parsed double
            while (true) {
                System.out.print("What confidence level would you like? (0.1 to 1) ");
                String confidence = scanner.nextLine();
                try {
                    confidenceNum = Double.parseDouble(confidence);
                    if (confidenceNum >= 0.1 && confidenceNum <= 1) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a double between 0.1 and 1.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid double.");
                }
            }

            while (true) {
                System.out.print("What would you like to make the symbol of the horse? ");
                String symbol = scanner.nextLine();

                if (symbol.length() == 1) {
                    symbolChar = symbol.charAt(0);
                    break;  // Exit the loop if input is valid
                }

                System.out.println("Invalid input. Please enter exactly one character.");
            }
            
            horses[x] = new Horse(symbolChar, name, confidenceNum);
        }
        System.out.print("\033[H\033[2J");
        return horses;
    }

    public static int trackSettings(){
        Scanner scanner = new Scanner(System.in);
        trackLength = 5;
        while(true){
            System.out.print("What length would you like the track to be? (between 5-15) ");
            try{
                trackLength = scanner.nextInt();
                if(trackLength>4 && trackLength < 16){
                    break;
                }else{
                    System.out.println("Please enter a number between 5 and 15");
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter in a whole number only");
                scanner.next();
            }
        }
        System.out.print("\033[H\033[2J");
        return trackLength;
    }

    public static void startSimulation(Race race, Horse[] horse){
        for(int x=0; x<3;  x++){
            race.addHorse(horse[x], x+1);
        }

        race.startRace();
    }

    public static void startMenu(){

        while(true){
            System.out.println("Welcome to the horse simulation, what would you like to do?");
            System.out.println("1. Start simulation (Note: Horses must be added, track length is defaulted at 5)");
            System.out.println("2. Modify track length");
            System.out.println("3. Add Horses");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter choice:");
            String answer = scanner.nextLine();

            if(answer.equals("1")){
                if(horseInitialised == false){
                    System.out.print("\033[H\033[2J");
                    System.out.println("You must add horses.");
                }else{
                    Race race = new Race(trackLength);
                    startSimulation(race, horselist);
                }

            }else if(answer.equals("2")){
                trackLength = trackSettings();
            }else if(answer.equals("3")){
                horselist = horseInitialisation();
                horseInitialised = true;
            }else{
                System.out.print("\033[H\033[2J");
                System.out.println("Please enter a valid choice");
            }
        }

    }
}
