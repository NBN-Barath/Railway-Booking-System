import Compartment.Compartment;

import java.util.ArrayList;

public class Train {
    private String trainName;
    private String trainId;
    private int numberOfCompartments;
    ArrayList<Compartment> compartments = new ArrayList<>();
    ArrayList<Stop> path = new ArrayList<>();

    public Train(String trainName, String trainId, int numberOfCompartments, ArrayList<Compartment> compartments) {
        this.trainName = trainName;
        this.trainId = trainId;
        this.numberOfCompartments = numberOfCompartments;
        this.compartments = compartments;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
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

    public void setCompartments(ArrayList<Compartment> compartments) {
        this.compartments = compartments;
    }

    public ArrayList<Stop> getPath() {
        return path;
    }

    public void setPath(ArrayList<Stop> path) {
        this.path = path;
    }
}
