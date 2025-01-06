package bennett.citibike.json;

public class Status {
    public String stationId;
    public int availableBikes;
    public int availableSlots;

    public Status(String stationId, int availableBikes, int availableSlots) {
        this.stationId = stationId;
        this.availableBikes = availableBikes;
        this.availableSlots = availableSlots;
    }
}
