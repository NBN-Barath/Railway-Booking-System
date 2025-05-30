import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class RBS_Actions {
    static ArrayList<Accounts> accountsList = RBS.getAccountsArrayList();
    static HashMap<String,Train> trainHashMap = RBS.getTrainHashMap();

    public static void start(){
        Scanner scanner = new Scanner(System.in);
        CommonActions commonActions = new CommonActions();
        accountsList.add(new AdminAccount("Admin","1","1","1","1"));

        while (true) {
            System.out.println(" 1.Login \n 2.Register \n 3.Exit\n Enter the choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) {
                Accounts loginAccount = commonActions.login(accountsList,scanner);
                if (loginAccount != null) {
                    checkWhoYouAre(loginAccount,scanner);
                }
            } else if (choice == 2) {
                UserAction.registerUser(accountsList,scanner);
            } else if (choice == 3) {
                System.out.println("Thank You for using");
                return;
            } else {
                System.out.println("Enter a valid choice");
                return;
            }
        }
    }

    public static void checkWhoYouAre(Accounts loginAccount,Scanner scanner){
        if (loginAccount instanceof AdminAccount){
            adminAction((AdminAccount) loginAccount,scanner);
        }else if(loginAccount instanceof UserAccount) {
            userAction((UserAccount) loginAccount,scanner);
        }
    }

    public static void adminAction(AdminAccount loginAccount,Scanner scanner){
        System.out.println("Admin Actions:");
        System.out.println("==============");
        while (true){
            System.out.println(" 1) Add new Admin \n 2) View all Admin \n 3) Delete Admin \n 4) Add Train \n 5) View Train \n 6) Change path \n 7) Exit");
            System.out.print("Enter your choice : ");
            int adminChoice = scanner.nextInt();
            switch (adminChoice){
                case 1:
                    AdminActions.addNewAdmin(accountsList,scanner);
                    break;
                case 2:
                    AdminActions.viewAllAdmins(accountsList);
                    break;
                case 3:
                    AdminActions.deleteAdmin(accountsList,scanner);
                    break;
                case 4:
                    AdminActions.addTrain(trainHashMap,scanner);
                    break;
                case 5:
                    AdminActions.viewTrains(trainHashMap);
                    break;
//                case 6:
//                    AdminActions.deleteTrain(trainHashMap,scanner);
//                    break;
                case 6:
                    AdminActions.editTrain(trainHashMap,scanner);
                    break;
                case 7:
                    System.out.println("Exiting");
                    return;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }

    }

    public static void userAction(UserAccount loginAccount,Scanner scanner){
        System.out.println("User Actions:");
        System.out.println("==============");
        while (true) {
            System.out.println("1.View Train");
            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    UserAction.viewTrain(loginAccount,trainHashMap,scanner);
                    break;
            }
        }

    }

}
