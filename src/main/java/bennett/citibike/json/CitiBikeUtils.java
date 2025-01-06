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

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth's radius in km

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // Result in kilometers
    }


    public Optional<Station> findClosestStationWithSlots(
            double userLat, double userLon, List<Station> stations, List<Status> statuses) {

        Station closestStation = null;
        double minDistance = Double.MAX_VALUE;

        for (Station station : stations) {
            // Match station with its status
            Status matchingStatus = statuses.stream()
                    .filter(status -> status.stationId.equals(station.stationId))
                    .findFirst()
                    .orElse(null);

            // Only consider stations with available slots
            if (matchingStatus != null && matchingStatus.availableSlots > 0) {
                // Calculate distance using Haversine formula
                double distance = CitiBikeUtils.calculateDistance(userLat, userLon, station.lat, station.lon);

                // Update closest station if distance is smaller
                if (distance < minDistance) {
                    minDistance = distance;
                    closestStation = station;
                }
            }
        }

        return Optional.ofNullable(closestStation);
    }

}

