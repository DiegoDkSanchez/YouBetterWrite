package sv.dk.com.youbetterwrite;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import sv.dk.com.youbetterwrite.Modelos.Historia;
import sv.dk.com.youbetterwrite.Modelos.ResponseCategory;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.ResponseDataSingleStory;

/**
 * Created by DK-Ragnar on 8/9/2018.
 */

public interface ServicioHistorias {

    String base_url = "http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/api/";

    @GET("stories")
    Call<ResponseData> getHistorias();

    @GET("stories/1")
    Call<ResponseData> getBetter();

    @GET("categories")
    Call<ResponseCategory> getCategories();

    @FormUrlEncoded
    @POST("stories")
    Call<ResponseDataSingleStory> addHistoria(
            @Field("name") String name,
            @Field("state") int value,
            @Field("id_category") int category_id);


    @FormUrlEncoded
    @POST("sections")
    Call<ResponseBody> addSection(
            @Field("name") String name,
            @Field("description") String description,
            @Field("id_story") int id_story);

    @FormUrlEncoded
    @POST("usuario_stories")
    Call<ResponseBody> addClaps(
            @Field("id_usuaio") int id_usuario,
            @Field("id_story") int id_story,
            @Field("comentario") String comentario,
            @Field("state") int state);

    @Multipart
    @POST("stories")
    Call<ResponseBody> addStory (
            @Part("file\"; filename=\"pp.png\" ") RequestBody file ,
            @Part("name") RequestBody name,
            @Part("state") RequestBody state,
            @Part("id_category") RequestBody category_id);


}
