import Compartment.Compartment;

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
        UserAccount usersAccount = new UserAccount(registeringUserName, registeringUserId, registerUserPin, registerUserPhoneNumber, registerUserEmailId);
        accountsArrayList.add(usersAccount);
        System.out.println("User successfully registered!");
    }
    
    public static void viewTrain(UserAccount loggedUser , HashMap<String,Train> trainHashMap , Scanner scanner){
        System.out.println("\t========== Viewing Train ==========");
        System.out.print("Enter the location to view train:");
        String location = scanner.next();
        for ( Train train : trainHashMap.values()){
            for (Stop stop : train.getPath()){
                if(location.equalsIgnoreCase(stop.getStationName())){
                    train.displayTrainDetails();
                    bookingTicket(loggedUser,trainHashMap,scanner);
                }
            }
        }
        System.out.println("No train data currently available at the station");
    }

    public static void bookTicket(UserAccount loggedUser , HashMap<String,Train> trainHashMap , Scanner scanner){
        System.out.println("\t========== Choose Ticket For Booking Ticket ==========");
        viewTrain(loggedUser,trainHashMap,scanner);
    }

    public static void bookingTicket(UserAccount loggedUser ,HashMap<String,Train> trainHashMap , Scanner scanner){
        System.out.println("\t========== Book Ticket ==========");
        String trainId;
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
            System.out.println("Train Id not found. Please Try Again");
        }
        System.out.println("You selected Train :"+trainHashMap.get(trainId).getTrainName() );
        trainHashMap.get(trainId).displayTrainDetails();

        System.out.println("Available Compartments:");
        ArrayList<Compartment> compartments = trainHashMap.get(trainId).getCompartments();

        for (int i = 0; i < compartments.size(); i++) {
            System.out.println((i + 1) + ". " + compartments.get(i).getClassType().getName());
        }

        System.out.print("Choose compartment by number: ");
        int compChoice = scanner.nextInt();
        Compartment selectedCompartment = compartments.get(compChoice - 1);

        String start






    }
}
