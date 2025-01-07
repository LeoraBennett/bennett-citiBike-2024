package bennett.citibike.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CitiBikeServiceTest {

    @Test
    void getStationInformation() {
        // Given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // When
        StationResponse response = service.getStationInformation()
                .blockingGet();

        // Then
        assertNotNull(response, "StationResponse should not be null.");
        assertNotNull(response.data, "Data object should not be null.");
        assertNotNull(response.data.stations, "Stations array should not be null.");
        assertFalse(response.data.stations.isEmpty(), "Stations array should not be empty.");
        assertNotNull(response.data.stations.get(0).station_id, "Station id");
    }

    @Test
    void getStationStatus() {
        // Given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // When
        StatusResponse response = service.getStationStatus()
                .blockingGet();

        // Then
        assertNotNull(response, "StatusResponse should not be null.");
        assertNotNull(response.data, "Data object should not be null.");
        assertNotNull(response.data.stations, "Stations array should not be null.");
        assertTrue(response.data.stations.size() > 0, "Stations array should not be empty.");
        assertNotNull(response.data.stations.get(0).station_id);
    }
}
