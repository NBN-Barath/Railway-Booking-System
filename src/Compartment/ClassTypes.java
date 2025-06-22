package Compartment;

public class ClassTypes {

    private String name;
    private int ratePerStop;

    // Constructor
    public ClassTypes(String name, int ratePerStop) {
        this.name = name;
        this.ratePerStop = ratePerStop;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for rate per stop
    public int getRatePerStop() {
        return ratePerStop;
    }

    // Static method to return all available ClassTypes
    public static ClassTypes[] getAllClassTypes() {
        return new ClassTypes[] {
                new ClassTypes("VistadomeCoach", 150),
                new ClassTypes("FirstClass", 120),
                new ClassTypes("GeneralClass", 30),
                new ClassTypes("SleeperClass", 50)
        };
    }

    // Optional: Find by name
    public static ClassTypes getClassTypeByName(String name) {
        for (ClassTypes classType : getAllClassTypes()) {
            if (classType.getName().equalsIgnoreCase(name)) {
                return classType;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
