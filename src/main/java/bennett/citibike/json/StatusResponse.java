package bennett.citibike.json;

import java.util.List;

public class StatusResponse {
    public Data data;

    public static class Data {
        public List<Status> stations; // List of station statuses
    }
}
