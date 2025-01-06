package bennett.citibike.json;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CitiBikeUtilsTest {

    @Test
    void findStationStatus() {
        List<Status> statusList = Arrays.asList(
                new Status("001", 5, 10),
                new Status("002", 0, 15),
                new Status("003", 3, 8)
        );

        CitiBikeUtils utils = new CitiBikeUtils();

        Optional<Status> status = utils.findStationStatus("002", statusList);

        assertTrue(status.isPresent());
        assertEquals(0, status.get().availableBikes);
        assertEquals(15, status.get().availableSlots);
    }

    @Test
    void findClosestStationWithBikes() {
        List<Station> stations = Arrays.asList(
                new Station("001", "Station A", 40.7128, -74.0060),
                new Station("002", "Station B", 40.7138, -74.0070),
                new Station("003", "Station C", 40.7148, -74.0080)
        );

        List<Status> statuses = Arrays.asList(
                new Status("001", 0, 10),
                new Status("002", 5, 15),
                new Status("003", 3, 8)
        );

        CitiBikeUtils utils = new CitiBikeUtils();

        Optional<Station> closestStation = utils.findClosestStationWithBikes(40.7130, -74.0070, stations, statuses);

        assertTrue(closestStation.isPresent());
        assertEquals("Station B", closestStation.get().name);  // Station B has 5 bikes
    }

    @Test
    void findClosestStationWithSlots() {
        // Arrange: Define stations and statuses
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

        // Act: Find the closest station with available slots
        Optional<Station> closestStation = utils.findClosestStationWithSlots(
                40.7130, -74.0070, stations, statuses);

        // Assert: Verify the closest station is Station B
        assertTrue(closestStation.isPresent(), "There should be a closest station.");
        assertEquals("Station B", closestStation.get().name,
                "Station B should be the closest station with slots.");
    }


}

