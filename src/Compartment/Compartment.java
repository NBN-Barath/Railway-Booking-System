package Compartment;


import java.util.ArrayList;
import java.util.HashMap;

public class Compartment {
    private Train belongingTrain;
    private String compartmentId;
    private ClassTypes classType;
    private int totalSeats;
    private int availableSeats;
    private HashMap<String, ArrayList<String>> seatArrangement;
    private String gridLayout;


    public Compartment(Train trainId, String compartmentId, ClassTypes classType, int totalSeats, HashMap<String, ArrayList<String>> seatArrangement,String gridLayout) {
        this.belongingTrain = trainId;
        this.compartmentId = compartmentId;
        this.classType = classType;
        this.totalSeats = totalSeats;
        this.seatArrangement = seatArrangement;
        this.gridLayout = gridLayout;
    }

    public Train getBelongingTrain() {
        return belongingTrain;
    }

    public void setBelongingTrain(Train beloingTrain) {
        this.belongingTrain = beloingTrain;
    }

    public String getCompartmentId() {
        return compartmentId;
    }

    public void setCompartmentId(String compartmentId) {
        this.compartmentId = compartmentId;
    }

    public ClassTypes getClassType() {
        return classType;
    }

    public void setClassType(ClassTypes classType) {
        this.classType = classType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public HashMap<String, ArrayList<String>> getSeatArrangement() {
        return seatArrangement;
    }

    public void setSeatArrangement(HashMap<String, ArrayList<String>> seatArrangement) {
        this.seatArrangement = seatArrangement;
    }

    public String getGridLayout() {
        return gridLayout;
    }

    public void setGridLayout(String gridLayout) {
        this.gridLayout = gridLayout;
    }

}