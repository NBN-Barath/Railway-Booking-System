import java.util.ArrayList;
import java.util.HashMap;

public class Utility {

    public HashMap<String, ArrayList<String>> generateSeatingPatterns(int noOfSeats, String screenGrid) {
        int remainingSeats = noOfSeats;

        // Split and parse grid safely
        String[] splitGrid = screenGrid.split("\\|");

        int seatsPerRow = 0;
        try {
            for (String grid : splitGrid) {
                seatsPerRow += Integer.parseInt(grid.trim());
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid grid format. Please use format like 3|2|3 with only numbers.");
            return null;  // Return null if grid is invalid
        }

        // Validate seat count
        if (seatsPerRow == 0 || remainingSeats % seatsPerRow != 0) {
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
                    rowSeats.add(rowLabel + "[" + seatNumber++ + "]");
                }

                if (i < splitGrid.length - 1) {
                    rowSeats.add(" <==> ");
                }
            }

            seatArrangement.put(rowLabel, rowSeats);
            rowIndex++;
            remainingSeats -= seatsPerRow;
        }

        return seatArrangement;
    }

    private String getRowLabel(int index) {
        StringBuilder label = new StringBuilder();
        while (index >= 0) {
            label.insert(0, (char) ('A' + (index % 26)));
            index = (index / 26) - 1;
        }
        return label.toString();
    }
}
