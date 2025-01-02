package bennett.citibike.json;

public class Status {
    public String station_id;
    public int available_bikes;
    public int available_slots;

    public Status(String station_id, int available_bikes, int available_slots) {
        this.station_id = station_id;
        this.available_bikes = available_bikes;
        this.available_slots = available_slots;
    }
}
