package bennett.citibike.json;

public class Station {
    public String stationId;
    public String name;
    public double lat;
    public double lon;

    public Station(String stationId, String name, double lat, double lon) {
        this.stationId = stationId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
