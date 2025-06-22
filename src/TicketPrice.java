import Compartment.ClassTypes;

public class TicketPrice {
    public static int calculatePrice(ClassTypes classType, int stops, int seats) {
        return classType.getRatePerStop() * stops * seats;
    }
}
