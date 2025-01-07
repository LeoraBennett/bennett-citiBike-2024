package bennett.citibike.json;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CitiBikeUtilsTest {

    @Test
    void findStationStatus() {
        // Given
        List<Status> statusList = Arrays.asList(
                new Status("001", 5, 10),
                new Status("002", 0, 15),
                new Status("003", 3, 8)
        );

        CitiBikeUtils utils = new CitiBikeUtils();

        // When
        Optional<Status> status = utils.findStationStatus("002", statusList);

        // Then
        assertTrue(status.isPresent());
        assertEquals(0, status.get().numBikesAvailable);
        assertEquals(15, status.get().numDocksAvailable);
    }

    @Test
    void findClosestStationWithBikes() {
        // Given
        List<Station> stations = Arrays.asList(
                new Station("001", "Station A", 40.7128, -74.0060),
                new Station("002", "Station B", 40.7138, -74.0070),
                new Station("003", "Station C", 40.7148, -74.0080)
        );

        // When
        List<Status> statuses = Arrays.asList(
                new Status("001", 0, 10),
                new Status("002", 5, 15),
                new Status("003", 3, 8)
        );

        CitiBikeUtils utils = new CitiBikeUtils();

        // Then
        Optional<Station> closestStation = utils.findClosestStationWithBikes(40.7130, -74.0070, stations, statuses);

        assertTrue(closestStation.isPresent());
        assertEquals("Station B", closestStation.get().name);  // Station B has 5 bikes
    }

    @Test
    void findClosestStationWithSlots() {
        // Given
        List<Station> stations = Arrays.asList(
                new Station("001", "Station A", 40.7128, -74.0060), // Farther
                new Station("002", "Station B", 40.7135, -74.0065), // Closer
                new Station("003", "Station C", 40.7150, -74.0080)  // Farther
        );

        List<Status> statuses = Arrays.asList(
                new Status("001", 0, 10),  // Station A has slots
                new Status("002", 0, 15), // Station B has slots
                new Status("003", 0, 8)   // Station C has slots
        );

        CitiBikeUtils utils = new CitiBikeUtils();

        // When
        Optional<Station> closestStation = utils.findClosestStationWithSlots(
                40.7130, -74.0070, stations, statuses);

        // Then
        assertTrue(closestStation.isPresent(), "There should be a closest station.");
        assertEquals("Station B", closestStation.get().name,
                "Station B should be the closest station with slots.");
    }


}

