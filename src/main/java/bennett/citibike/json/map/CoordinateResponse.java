package bennett.citibike.json.map;

public class CoordinateResponse {
    public String rentStation;
    public String returnStation;

    public CoordinateResponse(String rentStation, String returnStation) {
        this.rentStation = rentStation;
        this.returnStation = returnStation;
    }
}

