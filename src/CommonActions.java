import java.util.ArrayList;
import java.util.Scanner;

public class CommonActions {
    public Accounts login(ArrayList<Accounts> accountsGenericList, Scanner scanner) // common login to user and admin
    {
        System.out.println("Enter the User Id");
        String id = scanner.next();// get id
//        if (!accountsGenericList.equals(null)) {
            for (Accounts accounts : accountsGenericList) // iterate all account
            {
                if (accounts.getId().equals(id)) { // check id
                    System.out.print("Enter User PIN: ");
                    String userPin = scanner.next();

                    if (accounts.getPassword().equals(userPin)) { // Check admin pin is valid
                        System.out.println("User login successful.");
                        System.out.println("\tUser Name:" + accounts.getName() + "\n\tUser Id:" + accounts.getId());
                        return accounts;// return user when login is successful
                    } else {
                        System.out.println("Entered wrong Id or Password ");
                    }
                }
            }
            return null;
//        } else {
//            System.out.println("No id found");
//            System.out.println("Want to register(Yes or no):");{
//                if (scanner.next().equalsIgnoreCase("yes")){
//                    UserAction.registerUser(accountsGenericList,scanner);
//                }
//            }
//            return null;
//        }
    }

}
