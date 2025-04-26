import java.util.ArrayList;
import java.util.Scanner;

public class CommonActions {

    // Common login method for both users and admins
    public Accounts login(ArrayList<Accounts> accountsGenericList, Scanner scanner) {
        System.out.print("Enter the User ID: ");
        String id = scanner.next();

        for (Accounts account : accountsGenericList) {
            if (account.getId().equals(id)) {
                System.out.print("Enter User PIN: ");
                String userPin = scanner.next();

                if (account.getPassword().equals(userPin)) {
                    System.out.println("Login successful!");
                    System.out.println("\tUser Name: " + account.getName());
                    System.out.println("\tUser ID  : " + account.getId());
                    return account;
                } else {
                    System.out.println("Incorrect PIN. Please try again.");
                    return null;
                }
            }
        }

        // If no matching ID found
        System.out.println("No account found with ID: " + id);
        System.out.print("Would you like to register? (yes/no): ");
        String choice = scanner.next();

        if (choice.equalsIgnoreCase("yes")) {
            UserAction.registerUser(accountsGenericList, scanner);
        } else {
            System.out.println("Returning to main menu.");
        }
        return null;
    }
}
