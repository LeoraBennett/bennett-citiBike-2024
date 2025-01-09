package bennett.citibike.json.map;

import bennett.citibike.json.CitiBikeService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LambdaServiceFactory {

    private static final String BASE_URL = "https://h7moph2lrp6tppa67go74i7nmy0tkifo.lambda-url.eu-north-1.on.aws/";

    public LambdaService createLambdaService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CitiBikeService citiBikeService = retrofit.create(CitiBikeService.class);

        return new LambdaService();
    }
}
