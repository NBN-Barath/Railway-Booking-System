import POJO.Train;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class RBS {

    private static ArrayList<Accounts> accountsArrayList = new ArrayList<>();
    private static HashMap<String, Train> trainHashMap = new HashMap<>();
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm"); // store the time


    public static ArrayList<Accounts> getAccountsArrayList() {
        return accountsArrayList;
    }

    public static HashMap<String, Train> getTrainHashMap() {
        return trainHashMap;
    }

    public static DateTimeFormatter getTimeFormatter() {
        return timeFormatter;
    }

}
