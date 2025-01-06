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

    // Generalized helper method for finding closest station
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
                            && (isBikeSearch ? stationStatus.get().numBikesAvailable > 0 :
                            stationStatus.get().numDocksAvailable > 0);
                })
                .min(Comparator.comparingDouble(station -> calculateDistance(lat, lon,
                        station.lat, station.lon)));
    }

    // Find the closest station (with available slots) to a given location
    public Optional<Station> findClosestStationWithSlots(
            double userLat, double userLon, List<Station> stations, List<Status> statuses) {

        return stations.stream()
                // Filter stations with available slots
                .filter(station -> {
                    Optional<Status> matchingStatus = findStationStatus(station.stationId, statuses);
                    return matchingStatus.isPresent() && matchingStatus.get().numDocksAvailable > 0;
                })
                // Find the station with the minimum distance
                .min(Comparator.comparingDouble(station -> calculateDistance(
                        userLat, userLon, station.lat, station.lon)));
    }

    private static final int EARTH_RADIUS_KM = 6371; // Radius of the Earth in kilometers

    /**
     * Calculates the distance between two points specified by latitude and longitude using the Haversine formula.
     *
     * @param lat1 Latitude of the first point
     * @param lon1 Longitude of the first point
     * @param lat2 Latitude of the second point
     * @param lon2 Longitude of the second point
     * @return Distance in kilometers
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
