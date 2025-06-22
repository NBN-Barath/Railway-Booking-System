import java.time.LocalTime;
import java.util.Scanner;

public class ValidInputs {
    public static LocalTime getValidTime(Scanner scanner, String stringToBeDisplayed) { // getting time
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

    public static int getValidInteger(Scanner scanner, String errorMessage) {// method to get positive integer
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
