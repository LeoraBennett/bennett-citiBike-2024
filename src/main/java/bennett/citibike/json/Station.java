package bennett.citibike.json;

public class Station {
    public String station_id;
    public String name;
    public double lat;
    public double lon;

    public Station(String stationId, String name, double lat, double lon) {
        this.station_id = stationId;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
