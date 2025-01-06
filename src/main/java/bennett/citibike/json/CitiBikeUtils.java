package bennett.citibike.json;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CitiBikeUtils {

    // Find the status of a station given the station_id
    public Optional<Status> findStationStatus(String stationId, List<Status> statusList) {
        return statusList.stream()
                .filter(status -> status.stationId.equals(stationId))
                .findFirst();
    }

    // Find the closest station (with available bikes) to a given location
    public Optional<Station> findClosestStationWithBikes(double lat,
                                                         double lon,
                                                         List<Station> stations,
                                                         List<Status> statusList) {
        return findClosestStation(lat, lon, stations, statusList, true);
    }

    // Find the closest station (with available slots) to a given location
    public Optional<Station> findClosestStationWithSlots(double lat,
                                                         double lon,
                                                         List<Station> stations,
                                                         List<Status> statusList) {
        return findClosestStation(lat, lon, stations, statusList, false);
    }

    private Optional<Station> findClosestStation(double lat,
                                                 double lon,
                                                 List<Station> stations,
                                                 List<Status> statusList,
                                                 boolean isBikeSearch) {
        return stations.stream()
                .filter(station -> {
                    Optional<Status> stationStatus = findStationStatus(
                            station.stationId, statusList);
                    return stationStatus.isPresent()
                            && (isBikeSearch ? stationStatus.get().availableBikes > 0 :
                                    stationStatus.get().availableSlots > 0);
                })
                .min(Comparator.comparingDouble(station -> calculateDistance(lat, lon,
                        station.lat, station.lon)));
    }

    // Helper to calculate distance between two points
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515; // Convert to miles
        return dist;
    }
}

