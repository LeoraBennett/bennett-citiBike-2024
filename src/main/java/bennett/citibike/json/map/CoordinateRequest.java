package bennett.citibike.json.map;

public class CoordinateRequest {
    public double startLat;
    public double startLng;
    public double endLat;
    public double endLng;

    public CoordinateRequest(double startLat, double startLng, double endLat, double endLng) {
        this.startLat = startLat;
        this.startLng = startLng;
        this.endLat = endLat;
        this.endLng = endLng;
    }
}
