package POJO;

import java.time.LocalTime;

public class Stop {
    private String stationName;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public Stop(String stationName, LocalTime arrivalTime, LocalTime departureTime) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public String getStationName() {
        return stationName;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    @Override
    public String toString() {
        return "POJO.Stop{" +
                "stationName='" + stationName + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
