package bennett.citibike.json;

import java.util.List;

public class StationResponse {
    public Data data;

    public static class Data {
        public List<Station> stations; // List of stations returned by the API
    }
}


