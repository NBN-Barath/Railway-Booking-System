import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utility {

    public HashMap<String, ArrayList<String>> generateSeatingPatterns(int noOfSeats, String screenGrid) {
        int remainingSeats = noOfSeats;

        // Split and parse grid safely
        String[] splitGrid = screenGrid.split("\\|");
        int seatsPerRow = 0;
        try {
            for (String grid : splitGrid) {
                int seats = Integer.parseInt(grid.trim());
                if (seats <= 0) {
                    System.out.println("Error: Grid parts must be positive integers.");
                    return null;
                }
                seatsPerRow += seats;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid grid format. Please use format like 3|2|3 with only numbers.");
            return null;
        }

        // Validate grid and seat count
        if (splitGrid.length == 0 || seatsPerRow == 0) {
            System.out.println("Error: Grid cannot be empty.");
            return null;
        }
        if (remainingSeats % seatsPerRow != 0) {
            System.out.println("Error: Total seats must be a multiple of seats per row.");
            return null;
        }

        // Generate layout
        HashMap<String, ArrayList<String>> seatArrangement = new HashMap<>();
        int rowIndex = 0;

        while (remainingSeats > 0) {
            ArrayList<String> rowSeats = new ArrayList<>();
            String rowLabel = getRowLabel(rowIndex);
            int seatNumber = 1;

            for (int i = 0; i < splitGrid.length; i++) {
                int seatsInBlock = Integer.parseInt(splitGrid[i].trim());

                for (int j = 0; j < seatsInBlock; j++) {
                    rowSeats.add("[" + seatNumber++ + "]");
                }

                if (i < splitGrid.length - 1) {
                    rowSeats.add("<==>");
                }
            }

            seatArrangement.put(rowLabel, rowSeats);
            rowIndex++;
            remainingSeats -= seatsPerRow;
        }

        // Print layout for debugging
        for (var entry : seatArrangement.entrySet()) {
            System.out.print(entry.getKey() + ": [");
            List<String> row = entry.getValue();
            for (int i = 0; i < row.size(); i++) {
                System.out.print(row.get(i));
                if (i < row.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }

        return seatArrangement;
    }

    private String getRowLabel(int index) {
        // Simplified row labeling: A, B, ..., Z, AA, AB, ...
        if (index < 26) {
            return String.valueOf((char) ('A' + index));
        } else {
            StringBuilder label = new StringBuilder();
            while (index >= 0) {
                label.insert(0, (char) ('A' + (index % 26)));
                index = (index / 26) - 1;
            }
            return label.toString();
        }
    }
}