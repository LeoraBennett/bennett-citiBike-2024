package bennett.citibike.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CitiBikeServiceTest {

    @Test
    void getStationInformation() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // when
        StationResponse stationResponse = service.getStationInformation().blockingGet();

        // then
        assertNotNull(stationResponse, "StationResponse should not be null.");
        assertNotNull(stationResponse.stations, "Stations array should not be null.");
        assertNotEquals(0, stationResponse.stations.length, "Stations array should not be empty.");

        Station firstStation = stationResponse.stations[0];
        assertNotNull(firstStation.name, "Station name should not be null.");
        assertNotEquals(0, firstStation.lat, "Station latitude should not be 0.");
        assertNotEquals(0, firstStation.lon, "Station longitude should not be 0.");
    }

    @Test
    void getStationStatus() {
        // given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // when
        StatusResponse statusResponse = service.getStationStatus().blockingGet();

        // then
        assertNotNull(statusResponse, "StatusResponse should not be null.");
        assertNotNull(statusResponse.stations, "Status array should not be null.");
        assertNotEquals(0, statusResponse.stations.length, "Status array should not be empty.");

        Status firstStatus = statusResponse.stations[0];
        assertNotEquals(0, firstStatus.availableBikes, "Available bikes should not be 0.");
        assertNotEquals(0, firstStatus.availableSlots, "Available slots should not be 0.");
    }
}
