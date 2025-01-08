package bennett.citibike.json.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CitiBikeRequestHandlerTest {

    @Test
    void processRequest() throws IOException {
        // given
        String jsonBody = Files.readString(Path.of("src/test/resources/request.json"));
        Context mockContext = mock(Context.class);
        APIGatewayProxyRequestEvent mockEvent = mock(APIGatewayProxyRequestEvent.class);
        when(mockEvent.getBody()).thenReturn(jsonBody);

        CitiBikeRequestHandler requestHandler = new CitiBikeRequestHandler();

        // when
        CitiBikeResponse result = requestHandler.handleRequest(mockEvent, mockContext);

        // then
        assertEquals("Lenox Ave & W 146 St", result.start.name);
        assertEquals("Berry St & N 8 St", result.end.name);
        assertNotNull(result.end.name, "End station should not be null");
    }
}
