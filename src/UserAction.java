import java.util.ArrayList;
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
        System.out.println("Enter the Location of the User:");
        String registerUserLocation = scanner.next();

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
}
