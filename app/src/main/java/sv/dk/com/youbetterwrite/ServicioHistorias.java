package sv.dk.com.youbetterwrite;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sv.dk.com.youbetterwrite.Modelos.Historia;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;

/**
 * Created by DK-Ragnar on 8/9/2018.
 */

public interface ServicioHistorias {

    String base_url = "http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/api/";

    @GET("stories")
    Call<ResponseData> getHistorias();

    @GET("autores/{id}")
    Call<Historia> getAutor(@Path("id") String id);

    @FormUrlEncoded
    @POST("conceptos")
    Call<ResponseData> addConcepto(
            @Field("name") String name,
            @Field("value") double value,
            @Field("category_id") int category_id);

}
