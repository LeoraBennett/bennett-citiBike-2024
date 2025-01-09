package bennett.citibike.json.lambda;

import bennett.citibike.json.CitiBikeService;
import bennett.citibike.json.CitiBikeServiceFactory;
import bennett.citibike.json.CitiBikeUtils;
import bennett.citibike.json.Station;
import bennett.citibike.json.StationResponse;
import bennett.citibike.json.StatusResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.google.gson.Gson;

public class CitiBikeRequestHandler implements RequestHandler
        <APIGatewayProxyRequestEvent, CitiBikeResponse> {

    @Override
    public CitiBikeResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        String body = event.getBody();
        Gson gson = new Gson();
        CitiBikeRequest request = gson.fromJson(body, CitiBikeRequest.class);

        CitiBikeService service = new CitiBikeServiceFactory().getService();
        CitiBikeUtils utils = new CitiBikeUtils();

        Location fromLocation = request.from;
        Location toLocation = request.to;

        StationResponse stationResponse = service.getStationInformation().blockingGet();
        StatusResponse statusResponse = service.getStationStatus().blockingGet();

        Station startStation = utils.findClosestStationWithBikes(
                fromLocation.lat, fromLocation.lon,
                stationResponse.data.stations,
                statusResponse.data.stations
        ).orElse(null);

        Station endStation = utils.findClosestStationWithSlots(
                toLocation.lat,
                toLocation.lon,
                stationResponse.data.stations,
                statusResponse.data.stations
        ).orElse(null);

        CitiBikeResponse response = new CitiBikeResponse(fromLocation, toLocation, startStation, endStation);
        return response;
    }
}
