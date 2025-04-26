import Compartment.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



public class AdminActions {

    private static Utility utility = new Utility();

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
                System.out.println(accounts);//print details
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
        System.out.println("\t ========== Adding a train ==========");


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


        // Get number of compartments
        System.out.print("Enter the number of compartments: ");
        int noOfCompartments = getPositiveInteger(scanner, "Enter a positive number of compartments...");


        // Create and add the train
        Train train = new Train(trainName, trainId);
        trainHashMap.put(trainId, train);
        boolean isCompartmentAdded = false;
        while (!isCompartmentAdded){
            isCompartmentAdded = addCompartment(trainHashMap,train,noOfCompartments,scanner);
        }

        // Add path/stops
        boolean isPathAdded = false;
        while (!isPathAdded){
            isPathAdded = addPath(train, scanner);
        }


        System.out.println("Train added successfully!");
    }

    public static boolean addPath(Train train,Scanner scanner){
        // Get number of stops
        boolean isPathAdded = false;
        System.out.print("Enter the number of stops: ");
        int noOfStops = getPositiveInteger(scanner, "Enter a positive number of stops...");

        ArrayList<Stop> path = new ArrayList<>();
        for (int i = 0; i < noOfStops; i++) {
            System.out.print("Enter name of stop " + (i + 1) + ": ");
            String stationName = scanner.next();
            LocalTime arrival = getValidTime(scanner,"Enter arrival time at " + stationName + ": ");
            LocalTime departure = getValidTime(scanner,"Enter departure time from " + stationName + ": ");

            path.add(new Stop(stationName, arrival, departure));
        }
        train.setPath(path);
        train.setNoOfStops(noOfStops);
        isPathAdded = true;
        return isPathAdded;
    }

    public static void viewTrains(HashMap<String, Train> trainHashMap) {
        if (trainHashMap.isEmpty()) {
            System.out.println("No trains available to display.");
            return;
        }

        System.out.println("\t========== Train Details ==========");
        for (Train train : trainHashMap.values()) {
           train.displayTrainDetails();
        }
    }

//    public static void deleteTrain(HashMap<String, Train> trainHashMap,Scanner scanner){
//        System.out.println("\t========== Deleting Train ==========");
//        System.out.print("Enter the train id to delete:");
//        String trainId = scanner.next();
//        for(String id : trainHashMap.keySet()){
//            if(id.equals(trainId)){
//                trainHashMap.remove(id);
//                System.out.println("Train was deleted");
//            }
//        }
//    }

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
                System.out.println(errorMessage);
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

    public static void editTrain(HashMap<String, Train> trainHashMap,Scanner scanner){
        System.out.print("Enter the train Id to be changes made:");
        String id = scanner.next();
        Train trainToBeEdited = null;
        boolean isTrainPresent = false;
        for(String trainId : trainHashMap.keySet()){
            if(trainId.equalsIgnoreCase(id)){
                trainToBeEdited = trainHashMap.get(trainId);
                isTrainPresent = true;
            }
        }
        while(isTrainPresent){
            System.out.println("1. Compartment changes \n 2.Path Changes");
            int choice = getPositiveInteger(scanner , " The number should be positive");
            switch (choice){
                case 1:
                    compartmentChanges(trainToBeEdited,trainHashMap , scanner);
                    break;
                case 2:
                    changePath(trainHashMap,trainToBeEdited,scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println(" Enter a valid choice...");
                    break;
            }
        }
    }

    public static void compartmentChanges(Train trainToBeEdited,HashMap<String, Train> trainHashMap, Scanner scanner){
        System.out.println("1. Add new Compartment \n 2. Remove a compartment \n 3. Exit");
        int choice = getPositiveInteger(scanner , " The number should be positive");
        switch (choice){
            case 1:
                boolean isAdded = addCompartment( trainHashMap,trainToBeEdited,1,scanner);
                if (isAdded) {
                    trainToBeEdited.setNumberOfCompartments(trainToBeEdited.getNumberOfCompartments() + 1);
                }
                break;
            case 2:
                boolean isRemoved = removeCompartment(trainToBeEdited,scanner);
                if (isRemoved) {
                    trainToBeEdited.setNumberOfCompartments(trainToBeEdited.getNumberOfCompartments() - 1);
                }
                break;
            case 3:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Enter a valid choice...");
                break;
        }
    }

    public static boolean addCompartment(HashMap<String, Train> trainHashMap,Train trains,int noOfCompartments, Scanner scanner){
        System.out.println("\t========== Adding Compartment for Train ==========");
        boolean isCompartmentAdded = false;
        // Loop to add compartments
        for (int i = 0; i < noOfCompartments; i++) {
            System.out.println("Compartment No " + i + 1);
            System.out.print("\nEnter the compartment ID: ");
            String compartmentId = scanner.next();
            boolean isCompartmentExists = false;
            for (Train train : trainHashMap.values()) {
                for (Compartment compartment : train.getCompartments()) {
                    if (compartment.getCompartmentId().equalsIgnoreCase(compartmentId)) {
                        isCompartmentExists = true;
                        break;
                    }
                }
                if (isCompartmentExists) break;
            }

            if (isCompartmentExists) {
                System.out.println("Compartment ID already exists! Please enter a unique one.");
                i--;
                continue;
            }

            // Display class types
            System.out.println("\nAvailable Class Types:");
            ClassTypes[] classTypes = ClassTypes.getAllClassTypes();
            for (int j = 0; j < classTypes.length; j++) {
                System.out.println((j + 1) + ". " + classTypes[j]);
            }

            System.out.print("Enter your choice (1-" + classTypes.length + "): ");
            int choice = scanner.nextInt();
            ClassTypes selectedType;

            if (choice >= 1 && choice <= classTypes.length) {
                selectedType = classTypes[choice - 1];
            } else {
                System.out.println("Invalid choice. Try again.");
                i--;
                continue;
            }


            System.out.print("Enter the total number of seats: ");
            int numOfSeats = getPositiveInteger(scanner, "Number of seats must be a positive integer: ");

            System.out.print("Enter the grid (e.g: 2|2): ");
            String screenGrid = scanner.next();
            var grid = utility.generateSeatingPatterns(numOfSeats, screenGrid);

            if (grid == null) {
                System.out.println("Invalid grid! Please re-enter the compartment.");
                i--; // retry current compartment
                continue;
            }

            Compartment compartment = new Compartment(trains.getTrainId(), compartmentId, selectedType, numOfSeats, grid);
            trains.addCompartment(compartment);
            isCompartmentAdded = true;
            return isCompartmentAdded;
        }
        trains.setNumberOfCompartments(noOfCompartments);
        return isCompartmentAdded;
    }

    public static boolean removeCompartment(Train train, Scanner scanner){
        System.out.println("\t========== Removing compartment for Train ==========");
        boolean isCompartmentRemoved = false;
        while (true){
            for (var compartments : train.getCompartments()){
                int i = 1;
                System.out.println(i +")" + compartments);
                i++;
            }
            System.out.println("Enter the compartment id to be removed:");
            String id = scanner.next();
            for (var compartments : train.getCompartments()){
                if(compartments.getCompartmentId() .equalsIgnoreCase(id)){
                    train.removeCompartment(compartments);
                    System.out.println("Compartment removed successfully");
                    isCompartmentRemoved = true;
                    return isCompartmentRemoved;
                }
            }
            System.out.println("Compartment not found");
        }
    }

    public static void changePath(HashMap<String, Train> trainHashMap , Train train ,Scanner scanner){
        System.out.println("\t========== Changing path for Train ==========");
        boolean isPathAdded = false;
        while (!isPathAdded){
            isPathAdded = addPath(train,scanner);
        }
        System.out.println("Path changed Successfully");

    }
}
