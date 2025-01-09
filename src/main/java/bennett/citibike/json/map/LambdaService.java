package bennett.citibike.json.map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class LambdaService {
    public interface CitiBikeMapService {
        @POST("/")
        Single<CoordinateResponse> getRoute(@Body CoordinateRequest request);
    }
}
