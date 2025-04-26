import Compartment.Compartment;

import java.util.ArrayList;

public class Train {
    private String trainName;
    private String trainId;
    private int numberOfCompartments;
    int noOfStops;
    ArrayList<Compartment> compartments = new ArrayList<>();
    ArrayList<Stop> path = new ArrayList<>();

    public Train(String trainName, String trainId) {
        this.trainName = trainName;
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainId() {
        return trainId;
    }

    public int getNumberOfCompartments() {
        return numberOfCompartments;
    }

    public void setNumberOfCompartments(int numberOfCompartments) {
        this.numberOfCompartments = numberOfCompartments;
    }

    public ArrayList<Compartment> getCompartments() {
        return compartments;
    }

    public ArrayList<Stop> getPath() {
        return path;
    }

    public void setPath(ArrayList<Stop> path) {
        this.path = path;
    }

    public int getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(int noOfStops) {
        this.noOfStops = noOfStops;
    }

    public void addCompartment (Compartment compartments){
        this.compartments.add(compartments);
    }

    public void removeCompartment (Compartment compartment){
        this.compartments.remove(compartment);
    }
    
    public void displayTrainDetails(){
        System.out.println("\t========== Train Details of "+ getTrainName() +" ==========");
            System.out.println("Train Name      : " + getTrainName());
            System.out.println("Train ID        : " + getTrainId());
            System.out.println("Compartments    : " + getNumberOfCompartments());
            System.out.println("Stops           : " + getNoOfStops());

            System.out.println("----- Compartments -----");
            for (Compartment compartment :  getCompartments()) {
                System.out.println("Compartment ID   : " + compartment.getCompartmentId());
                System.out.println("Class Type       : " + compartment.getClassType());
                System.out.println("Seats            : " + compartment.getTotalSeats());
                System.out.println("Seating Arrangement:");
                for (var row : compartment.getSeatArrangement().entrySet()) {
                    System.out.print(row.getKey() + " : {");
                    ArrayList<String> rowSeats = row.getValue();
                    for (int i = 0; i < rowSeats.size(); i++) {
                        System.out.print(rowSeats.get(i));
                        if (i < rowSeats.size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println("}");
                }
                System.out.println();
            }

            System.out.println("----- Train Path / Stops -----");
            ArrayList<Stop> path = getPath();
            if (path != null && !path.isEmpty()) {
                for (int i = 0; i < path.size(); i++) {
                    Stop stop = path.get(i);
                    System.out.println((i + 1) + ". Station: " + stop.getStationName() + " | Arrival: " + stop.getArrivalTime() + " | Departure: " + stop.getDepartureTime());
                }
            } else {
                System.out.println("No route data available.");
            }

            System.out.println("-----------------------------------\n");
    }
}
