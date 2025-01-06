package bennett.citibike.json;

import bennett.citibike.json.StationResponse;
import bennett.citibike.json.StatusResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface CitiBikeService {

    @GET("station_information.json")
    Single<StationResponse> getStationInformation();

    @GET("station_status.json")
    Single<StatusResponse> getStationStatus();
}


