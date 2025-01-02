package bennett.citibike.json;

public class Station {
    public String station_id;
    public String name;
    public double lat; // Latitude
    public double lon; // Longitude

    public Station(String station_id, String name, double lat, double lon) {
        this.station_id = station_id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}
