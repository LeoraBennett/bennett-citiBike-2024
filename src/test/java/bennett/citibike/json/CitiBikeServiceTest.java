package bennett.citibike.json;

import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CitiBikeServiceTest {

    @Test
    void getStationInformation() {
        // Given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // When
        StationResponse response = service.getStationInformation()
                .subscribeOn(Schedulers.io())
                .blockingGet();

        // Then
        assertNotNull(response, "StationResponse should not be null.");
        assertNotNull(response.data, "Data object should not be null.");
        assertNotNull(response.data.stations, "Stations array should not be null.");
        assertFalse(response.data.stations.isEmpty(), "Stations array should not be empty.");
    }

    @Test
    void getStationStatus() {
        // Given
        CitiBikeService service = new CitiBikeServiceFactory().getService();

        // When
        StatusResponse response = service.getStationStatus()
                .subscribeOn(Schedulers.io())
                .blockingGet();

        // Then
        assertNotNull(response, "StatusResponse should not be null.");
        assertNotNull(response.data, "Data object should not be null.");
        assertNotNull(response.data.stations, "Stations array should not be null.");
        assertTrue(response.data.stations.size() > 0, "Stations array should not be empty.");
    }
}
