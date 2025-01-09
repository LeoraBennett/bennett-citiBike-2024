package bennett.citibike.json.lambda;

public class Location {

    public final double lat;
    public final double lon;

    private Location location;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}