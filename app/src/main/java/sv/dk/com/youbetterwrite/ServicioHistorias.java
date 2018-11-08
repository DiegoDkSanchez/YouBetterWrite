package sv.dk.com.youbetterwrite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sv.dk.com.youbetterwrite.Modelos.Historia;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;

/**
 * Created by DK-Ragnar on 8/9/2018.
 */

public interface ServicioHistorias {

    String base_url = "http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/story/public/api/";

    @GET("stories/")
    Call<ResponseData> getHistorias();

    @GET("autores/{id}")
    Call<Historia> getAutor(@Path("id") String id);

}
