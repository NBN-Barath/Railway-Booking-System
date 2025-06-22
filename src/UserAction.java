import Compartment.Compartment;
import POJO.Stop;
import POJO.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserAction {
    public static void registerUser(ArrayList<Accounts> accountsArrayList, Scanner scanner) {
        System.out.print("Enter the new User Name: ");
        String registeringUserName = scanner.next();
        System.out.print("Enter the new User Id: ");
        String registeringUserId = scanner.next();

        // Check if the User ID already exists
        for (Accounts account : accountsArrayList) {
            if (account.getId().equals(registeringUserId)) {
                System.out.println("User Id already exists... Try another User ID.");
                return;
            }
        }

        // Collecting additional user information
        System.out.print("Enter User PIN: ");
        String registerUserPin = scanner.next();
        System.out.print("Enter your Gender (Male - M or Female - F):");
        char g = scanner.nextLine().toUpperCase().charAt(0);
        Gender gender;
        if (g == 'M') {
            gender = Gender.Male;
        } else if (g == 'F') {
            gender = Gender.Female;
        } else {
            System.out.println("Invalid gender input.");
            return;
        }
        System.out.print("Enter your Email ID: ");
        String registerUserEmailId = scanner.next();
        System.out.print("Enter your Phone Number: ");
        String registerUserPhoneNumber = scanner.next();

        // Verification process
        System.out.println("User needs to verify:");
        System.out.println("1. Email ID\n2. Phone Number");
        boolean verificationSuccess = false;
        while (!verificationSuccess) {
            System.out.print("Choose your verification method (1 or 2): ");
            int verificationChoice = scanner.nextInt();
            int otp = VerificationClass.otpGenerator();

            if (verificationChoice == 1) {
                System.out.println("The OTP is sent to the Email ID.");
            } else if (verificationChoice == 2) {
                System.out.println("The OTP is sent to the Phone Number.");
            } else {
                System.out.println("Enter a valid choice.");
                continue;
            }

            System.out.println("The OTP to complete the registration is " + otp);
            System.out.print("Enter the OTP: ");
            int enteredOTP = scanner.nextInt();
            if (otp == enteredOTP) {
                System.out.println("Verification Complete");
                verificationSuccess = true;
            } else {
                System.out.println("Invalid OTP. Please try again.");
            }
        }

        // Creating and adding the new user account
        UserAccount usersAccount = new UserAccount(registeringUserName, registeringUserId, registerUserPin, registerUserPhoneNumber, registerUserEmailId,gender);
        accountsArrayList.add(usersAccount);
        System.out.println("User successfully registered!");
    }

    // Method to Viewing POJO.Train
    public static void viewTrain(UserAccount loggedUser , HashMap<String, Train> trainHashMap , Scanner scanner){
        System.out.println("\t========== Viewing POJO.Train ==========");
        System.out.print("Enter the location to view train:");
        String location = scanner.next();
        for ( Train train : trainHashMap.values()){
            for (Stop stop : train.getPath()){
                if(location.equalsIgnoreCase(stop.getStationName())){
                    train.displayTrainDetails();
                    bookingTicket(loggedUser,trainHashMap,scanner);
                    return;
                }
            }
        }
        System.out.println("No train data currently available at the station");
    }

    // Method to Book Ticket
    public static void bookingTicket(UserAccount loggedUser , HashMap<String, Train> trainHashMap , Scanner scanner){
        System.out.println("\t========== Book Ticket ==========");
        String trainId; // to store the train id to book ticket
        // Runn until valid input
        while (true){
            System.out.print("Enter the train Id to book ticket (or 0 to exit): ");
            trainId = scanner.next();
            if (trainId.equals("0")) {
                System.out.println("Exiting...");
                return;
            }
            if(trainHashMap.containsKey(trainId)){
                break;
            }
            System.out.println("POJO.Train Id not found. Please Try Again");
        }
        System.out.println("You selected POJO.Train :"+trainHashMap.get(trainId).getTrainName() );
        trainHashMap.get(trainId).displayTrainDetails();

        System.out.println("Available Compartments:");
        ArrayList<Compartment> compartments = trainHashMap.get(trainId).getCompartments();

        // Loop to view all types compartment in selected train
        for (int i = 0; i < compartments.size(); i++) {
            System.out.println((i + 1) + ". " + compartments.get(i).getClassType().getName());
        }

        System.out.print("Choose compartment by number: ");
        int compChoice = scanner.nextInt();
        Compartment selectedCompartment = compartments.get(compChoice - 1);

        // Calling seat selection method to handle seat booking
        ArrayList<String> selectedSeats = seatSelection(loggedUser,selectedCompartment.getSeatArrangement(),selectedCompartment, scanner);

        if (selectedSeats != null && !selectedSeats.isEmpty()) {
            // Proceed with confirmation after seat selection
            ConfirmBooking(selectedCompartment, selectedSeats, scanner);
        }
    }


    public static ArrayList<String> seatSelection(UserAccount loggedUser,HashMap<String, ArrayList<String>> seatArrangement, Compartment selectedCompartment, Scanner scanner) {
        System.out.println("\t========== Seat Selection ==========");

        ArrayList<String> selectedSeats = new ArrayList<>();
        int totalSeatsToBook = 0;

        // Count available seats
        for (var entry : seatArrangement.entrySet()) {
            for (String seat : entry.getValue()) {
                if (!(seat.equals("[M]") || seat.equals("[F]") || seat.equals("<==>"))) {
                    totalSeatsToBook++;
                }
            }
        }

        System.out.println("Total available seats: " + totalSeatsToBook);

        System.out.print("Enter number of seats you want to book (0 to exit): ");
        int seatsToBook = scanner.nextInt();

        if (seatsToBook == 0) {
            System.out.println("Exiting seat selection.");
            return null;
        }
        // If exceed the limit of seats
        if (seatsToBook > totalSeatsToBook) {
            System.out.println("Sorry, only " + totalSeatsToBook + " seats are available.");
            return null;
        }

        // Get grid structure with validation
        String gridLayout = selectedCompartment.getGridLayout();

        // For some execution
        if (gridLayout == null || gridLayout.trim().isEmpty()) {
            System.out.println("Error: Invalid grid layout.");
            return null;
        }
        //Splitting grid values
        String[] gridParts = gridLayout.split("\\|");
        int[] blockSizes = new int[gridParts.length];
        int totalSeatsPerRow = 0;
        try {
            for (int i = 0; i < gridParts.length; i++) {
                blockSizes[i] = Integer.parseInt(gridParts[i].trim());
                if (blockSizes[i] <= 0) {
                    System.out.println("Error: Grid parts must be positive integers.");
                    return null;
                }
                totalSeatsPerRow += blockSizes[i];
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid grid layout format.");
            return null;
        }

        // Validate seat arrangement
        for (ArrayList<String> row : seatArrangement.values()) {
            int expectedSize = totalSeatsPerRow + (blockSizes.length - 1); // Seats + aisles
            if (row.size() != expectedSize) {
                System.out.println("Error: Seat arrangement does not match grid layout.");
                return null;
            }
        }

        // Seat selection loop
        while (seatsToBook > 0) {
            // Display current seating=g layout
            displayFormattedSeats(seatArrangement, gridLayout);

            System.out.print("Enter seat number (e.g., A1, B2) or 'exit' to cancel: ");
            String input = scanner.next();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Seat selection canceled.");
                return null;
            }

            // Validate users input
            String seatNumber = input.toUpperCase();
            if (seatNumber.length() < 2 || !Character.isLetter(seatNumber.charAt(0))) {
                System.out.println("Invalid seat format! Use format like A1, B2.");
                continue;
            }

            // Searching for selected seat
            char seatRow = seatNumber.charAt(0);
            int seatColumn;
            try {
                seatColumn = Integer.parseInt(seatNumber.substring(1));
                if (seatColumn <= 0) {
                    System.out.println("Invalid seat number! Column must be positive.");
                    continue;
                }
                if (seatColumn > totalSeatsPerRow) {
                    System.out.println("Invalid column! Seat number exceeds available seats.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid seat format! Use format like A1, B2.");
                continue;
            }

            if (!seatArrangement.containsKey(String.valueOf(seatRow))) {
                System.out.println("Invalid row! Try again.");
                continue;
            }

            ArrayList<String> seatRowList = seatArrangement.get(String.valueOf(seatRow));
            // Calling a method to Adjusting column number
            int adjustedColumn = getAdjustedColumn(seatColumn, blockSizes);

            if (adjustedColumn < 0 || adjustedColumn >= seatRowList.size()) {
                System.out.println("Invalid column! Try again.");
                continue;
            }

            if (seatRowList.get(adjustedColumn).equals("[M]") || seatRowList.get(adjustedColumn).equals("[F]")) {
                System.out.println("Seat already booked! Choose another.");
                continue;
            }

            if (seatRowList.get(adjustedColumn).equals("<==>")) {
                System.out.println("Invalid selection! Cannot book an aisle.");
                continue;
            }
            if (selectedSeats.contains(seatNumber)) {
                System.out.println("Seat already selected in this session. Choose another.");
                continue;
            }

            boolean flag = false;
            while (!flag){
                System.out.print(input + " seat is for Male or Female (M/F): ");
                char gender = scanner.next().toUpperCase().charAt(0);
                if(gender == 'M' || gender == 'F'){
                    seatRowList.set(adjustedColumn, "["+gender+"]");
                    flag = true;
                }else {
                    System.out.println("Invalid input...");
                }
            }
            selectedSeats.add(seatNumber);
            seatsToBook--;

            System.out.println("Seat " + seatNumber + " has been successfully booked!");
        }

        // Final layout display
        System.out.println("\nFinal Seat Layout After Booking:");
        displayFormattedSeats(seatArrangement, gridLayout);

        System.out.println("Seats selected: " + selectedSeats);
        selectedCompartment.setSeatArrangement(seatArrangement);

        return selectedSeats;
    }

    // Method to Adjust Column
    private static int getAdjustedColumn(int seatColumn, int[] blockSizes) {
        if (blockSizes == null || blockSizes.length == 0 || seatColumn <= 0) {
            return -1; // Invalid input
        }
        int currentSeat = 0; // Tracks the 1-based seat number
        int currentIndex = 0; // Tracks the 0-based index in the ArrayList
        for (int i = 0; i < blockSizes.length; i++) {
            if (blockSizes[i] <= 0) {
                return -1; // Invalid block size
            }
            for (int j = 0; j < blockSizes[i]; j++) {
                currentSeat++;
                if (currentSeat == seatColumn) {
                    return currentIndex;
                }
                currentIndex++;
            }
            if (i < blockSizes.length - 1) {
                currentIndex++; // Skip the <==> marker
            }
        }
        return -1; // Invalid column (exceeds total seats)
    }

    // Method to Confirmation of booking after selecting seats
    public static void ConfirmBooking(Compartment selectedCompartment, ArrayList<String> selectedSeats, Scanner scanner) {
        System.out.println("\t========== Confirm Booking ==========");
        System.out.println("You selected the following seats: " + selectedSeats);
        System.out.println("POJO.Train: " + selectedCompartment.getBelongingTrain().getTrainName());
        System.out.println("Compartment: " + selectedCompartment.getClassType().getName());

        // Confirming the booking
        System.out.print("Do you confirm the booking? (1 = Yes / 0 = No): ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Booking successful! You have booked the following seats: " + selectedSeats);
        } else {
            System.out.println("Booking canceled.");
        }
    }

    // Method to display Formated seats
    public static void displayFormattedSeats(HashMap<String, ArrayList<String>> seatArrangement, String grid) {
        System.out.println("\n\t========== Current Seat Layout ==========");

        // Validate and parse grid
        if (grid == null || grid.trim().isEmpty()) {
            System.out.println("Error: Invalid grid layout.");
            return;
        }
        String[] gridParts = grid.split("\\|");
        int[] blockSizes = new int[gridParts.length];
        int totalSeats = 0;
        try {
            for (int i = 0; i < gridParts.length; i++) {
                blockSizes[i] = Integer.parseInt(gridParts[i].trim());
                if (blockSizes[i] <= 0) {
                    System.out.println("Error: Grid parts must be positive integers.");
                    return;
                }
                totalSeats += blockSizes[i];
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid grid layout format.");
            return;
        }

        // Calculate separator indices
        int[] separatorIndices = new int[blockSizes.length - 1];
        int currentIndex = 0;
        for (int i = 0; i < blockSizes.length - 1; i++) {
            currentIndex += blockSizes[i];
            separatorIndices[i] = currentIndex + i; // Account for previous aisles
        }

        // Display each row
        for (String rowKey : seatArrangement.keySet()) {
            ArrayList<String> rowSeats = seatArrangement.get(rowKey);
            System.out.print(rowKey + " : {");

            int seatNumber = 1;
            for (int i = 0; i < rowSeats.size(); i++) {
                // Print separator before the seat if at a separator index
                for (int sepIndex : separatorIndices) {
                    if (i == sepIndex) {
                        System.out.print("<==>");
                        if (i < rowSeats.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                }

                String seat = rowSeats.get(i);
                if (seat.equals("<==>")) {
                    continue; // Skip printing aisle markers
                }
                if (seat.equals("[X]")) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[" + seatNumber + "]");
                }
                seatNumber++;

                if (i < rowSeats.size() - 1 && !rowSeats.get(i + 1).equals("<==>")) {
                    System.out.print(", ");
                }
            }

            System.out.println("}");
        }
    }
}
