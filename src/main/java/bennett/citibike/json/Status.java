package bennett.citibike.json;

public class Status {
    public String stationId;
    public int numBikesAvailable;
    public int numDocksAvailable;

    public Status(String stationId, int numBikesAvailable, int numDocksAvailable) {
        this.stationId = stationId;
        this.numBikesAvailable = numBikesAvailable;
        this.numDocksAvailable = numDocksAvailable;
    }
}
