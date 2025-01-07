package bennett.citibike.json;

public class Status {
    //CHECKSTYLE:OFF
    public String station_id;
    public int num_bikes_available;
    public int num_docks_available;
    //CHECKSTYLE:ON

    public Status(String stationId, int numBikesAvailable, int numDocksAvailable) {
        this.station_id = stationId;
        this.num_bikes_available = numBikesAvailable;
        this.num_docks_available = numDocksAvailable;
    }
}
