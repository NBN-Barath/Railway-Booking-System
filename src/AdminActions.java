import java.util.ArrayList;
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

    public static void addTrain(HashMap<String,Train> trainHashMap,Scanner scanner) {
        System.out.println("\tAdding a train");
        System.out.print("\n Enter the Train Name:");
        String trainName = getValidName(scanner,"Not a valid name...");
        for (var checkName : trainHashMap.keySet()){
            if(!trainName.equalsIgnoreCase(checkName)){
                System.out.println("Enter the Train id:");
                String trainId = scanner.next();
                for (var checkId : trainHashMap.keySet()){
                    var temp = trainHashMap.get(checkId);
                    if(!temp.getTrain_Id().equalsIgnoreCase(trainId)){
                        System.out.print("Enter the number of stop of the train :");
                        int noOfStop = getPositiveInteger(scanner,"Enter the positive number...");

                    }else {
                        System.out.printf("The train with the id %s is already exist.. \n Train id must be unique... ",trainId);
                    }
                }
            } else {
                System.out.printf("The train with the name %s is already exist.. ",trainName);

            }
        }

    }

    public static String getValidName(Scanner scanner,String errorMessage){
        while (true) {
            String name = scanner.next();
            if (name.contains("*=")) {
                System.out.println("Not a valid name...");
            }else {
                return name;
            }
        }
    }

    public static int getPositiveInteger(Scanner scanner, String errorMessage) {// method to get positive integer
        try {
            int value = scanner.nextInt();
            if (value > 0) return value;
        } catch (Exception ignored) {
        }
        System.out.println(errorMessage);
        scanner.next(); // Clear invalid input
        return -1;
    }

}
