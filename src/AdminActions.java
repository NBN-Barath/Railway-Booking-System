import Compartment.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class AdminActions {

    public static void addNewAdmin(ArrayList<Accounts> accountsArrayList, Scanner scanner) {
        System.out.print("Enter the new Admin Name: ");
        String registeringUserName = scanner.next();
        System.out.print("Enter the new Admin Id: ");
        String registeringUserId = scanner.next();

        // Check if the User ID is already exists
        for (Accounts account : accountsArrayList)
        {
            if (account.getId().equals(registeringUserId)) {
                System.out.println("User Id already exists... Try another User ID.");
                return; // Exit the method
            }
        }

        // register the new admin
        System.out.print("Enter Admin PIN: ");
        String registerUserPin = scanner.next();
        System.out.print("Enter new Admin Email ID: ");
        String registerUserEmailId = scanner.next();
        System.out.print("Enter new Admin  Phone Number: ");
        String registerUserPhoneNumber = scanner.next();

        // Create the new user account
        AdminAccount usersAccount = new AdminAccount(registeringUserName, registeringUserId, registerUserPin, registerUserPhoneNumber, registerUserEmailId);

        // Add the new account to the list
        accountsArrayList.add(usersAccount);
        System.out.println("Admin successfully registered!");

    }

    public static void viewAllAdmins(ArrayList<Accounts> accountsArrayList){
        System.out.println("Available Admins");
        for (Accounts accounts: accountsArrayList){
            if(accounts instanceof AdminAccount){
                System.out.println(accounts.toString());//print details
            }
        }
    }

    public static void deleteAdmin(ArrayList<Accounts> accountsArrayList , Scanner scanner){
        System.out.println(" Enter the account do you want to delete:");
        String deletedAccount = scanner.next();
        for(Accounts accounts: accountsArrayList){
            if(accounts.getId().equalsIgnoreCase(deletedAccount)){
                accountsArrayList.remove(accounts);
            }
            else {
                System.out.println("Account not found");
            }
        }
    }

    public static void addTrain(HashMap<String, Train> trainHashMap, Scanner scanner) {
        System.out.println("\tAdding a train");

        // Train name input
        System.out.print("\nEnter the Train Name: ");
        String trainName = getValidName(scanner, "Not a valid name...");

        // Check if train name already exists
        boolean nameExists = false;
        for (Train t : trainHashMap.values()) {
            if (t.getTrainName().equalsIgnoreCase(trainName)) {
                nameExists = true;
                break;
            }
        }

        if (nameExists) {
            System.out.printf("The train with the name '%s' already exists.\n", trainName);
            return;
        }

        // Train ID input
        System.out.print("Enter the Train ID: ");
        String trainId = scanner.next();

        if (trainHashMap.containsKey(trainId)) {
            System.out.printf("The train with the ID '%s' already exists.\nTrain ID must be unique.\n", trainId);
            return;
        }

        // Get number of stops
        System.out.print("Enter the number of stops: ");
        int noOfStops = getPositiveInteger(scanner, "Enter a positive number of stops...");

        // Get number of compartments
        System.out.print("Enter the number of compartments: ");
        int noOfCompartments = getPositiveInteger(scanner, "Enter a positive number of compartments...");

        ArrayList<Compartment> compartmentArrayList = new ArrayList<>();
        Utility utility = new Utility();

        // Loop to add compartments
        for (int i = 0; i < noOfCompartments; i++) {
            System.out.print("\nEnter the compartment ID: ");
            String compartmentId = scanner.next();

            // Display class types
            System.out.println("\nAvailable Class Types:");
            ClassTypes[] classTypes = ClassTypes.values();
            for (int j = 0; j < classTypes.length; j++) {
                System.out.println((j + 1) + ". " + classTypes[j]);
            }

            System.out.print("Enter your choice (1-" + classTypes.length + "): ");
            int choice = scanner.nextInt();
            ClassTypes selectedType = null;

            if (choice >= 1 && choice <= classTypes.length) {
                selectedType = classTypes[choice - 1];
            } else {
                System.out.println("Invalid choice. Try again.");
                i--;
                continue;
            }

            System.out.print("Enter the total number of seats: ");
            int numOfSeats = getPositiveInteger(scanner, "Number of seats must be a positive integer: ");

            System.out.print("Enter the grid (e.g., 2|2, 3|3|3): ");
            String screenGrid = scanner.next();
            var grid = utility.generateSeatingPatterns(numOfSeats, screenGrid);

            if (grid == null) {
                System.out.println("Invalid grid! Please re-enter the compartment.");
                i--; // retry current compartment
                continue;
            }

            Compartment compartment = new Compartment(trainId, compartmentId, selectedType, numOfSeats, grid);
            compartmentArrayList.add(compartment);
        }

        // Create and add the train
        Train train = new Train(trainName, trainId, noOfCompartments, compartmentArrayList);
        trainHashMap.put(trainId, train);

        // Add path/stops
        addPath(train, noOfStops, scanner);

        System.out.println("Train added successfully!");
    }

    public static void addPath(Train train,int noOfStop,Scanner scanner){
        ArrayList<Stop> path = new ArrayList<>();
        for (int i = 0; i < noOfStop; i++) {
            System.out.print("Enter name of stop " + (i + 1) + ": ");
            String stationName = scanner.next();
            LocalTime arrival = getValidTime(scanner,"Enter arrival time at " + stationName + ": ");
            LocalTime departure = getValidTime(scanner,"Enter departure time from " + stationName + ": ");


            path.add(new Stop(stationName, arrival, departure));
            train.setPath(path);
        }

    }

    public static void viewTrains(HashMap<String, Train> trainHashMap) {
        if (trainHashMap.isEmpty()) {
            System.out.println("No trains available to display.");
            return;
        }

        System.out.println("========== Train Details ==========");
        for (Train train : trainHashMap.values()) {
            System.out.println("Train Name      : " + train.getTrainName());
            System.out.println("Train ID        : " + train.getTrainId());
            System.out.println("Compartments    : " + train.getNumberOfCompartments());

            System.out.println("----- Compartments -----");
            for (Compartment comp : train.getCompartments()) {
                System.out.println("Compartment ID   : " + comp.getCompartmentId());
                System.out.println("Class Type       : " + comp.getClassType());
                System.out.println("Seats            : " + comp.getTotalSeats());
                System.out.println();
            }

            System.out.println("----- Train Path / Stops -----");
            ArrayList<Stop> path = train.getPath();
            if (path != null && !path.isEmpty()) {
                for (int i = 0; i < path.size(); i++) {
                    Stop stop = path.get(i);
                    System.out.println((i + 1) + ". Station: " + stop.getStationName() + " | Arrival: " + stop.getArrivalTime() + " | Departure: " + stop.getDepartureTime());
                }
            } else {
                System.out.println("No route data available.");
            }

            System.out.println("-----------------------------------\n");
        }
    }

    public static void deleteTrain(HashMap<String, Train> trainHashMap,Scanner scanner){
        System.out.print("Enter the train id to delete:");
        String trainId = scanner.next();
        for(String id : trainHashMap.keySet()){
            if(id.equals(trainId)){
                trainHashMap.remove(id);
                System.out.print("Train was deleted");
            }
        }
    }

    public static LocalTime getValidTime(Scanner scanner,String stringToBeDisplayed) { // getting time
        while (true) {
            System.out.print(stringToBeDisplayed);
            try {
                String timeInput = scanner.next();
                return LocalTime.parse(timeInput, RBS.getTimeFormatter());
            } catch (Exception e) {
                System.out.println("Invalid time format!");
            }
        }
    }

    public static String getValidName(Scanner scanner,String errorMessage){
        while (true) {
            String name = scanner.next();
            if (name.contains("*=!-_+/")) {
                System.out.println("Not a valid name...");
            }else {
                return name;
            }
        }
    }

    public static int getPositiveInteger(Scanner scanner, String errorMessage) {// method to get positive integer
        while (true) {
            try {
                String input = scanner.next();
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                } else {
                    System.out.println(errorMessage);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format...");
            }
        }
    }
}
