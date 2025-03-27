import Compartment.Compartment;

import java.util.ArrayList;

public class Train {
    private String trainName;
    private String train_Id;
    private int no_Of_Compartment;
    ArrayList<Compartment> typeOfCompartment = new ArrayList<>();
    ArrayList<String> pathOfTrain = new ArrayList<>();

    public String getTrainName() {
        return trainName;
    }

    public String getTrain_Id() {
        return train_Id;
    }
}
