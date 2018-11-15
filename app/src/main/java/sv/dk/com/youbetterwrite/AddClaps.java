package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class AddClaps extends AppCompatActivity {

    private int contador = 0;
    private TextView title, txtContador;
    private Story historia;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ServicioHistorias.base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    ServicioHistorias apiService = retrofit.create(ServicioHistorias.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_claps);

        title = findViewById(R.id.tituloClaps);
        txtContador = findViewById(R.id.contador);

        historia = (Story) getIntent().getSerializableExtra("historia");
        title.setText(historia.getName());
    }

    public void addClap(View view) {
        if(contador < 50){
            contador += 1;
            txtContador.setText(contador+"");
            Call<ResponseBody> callSection = apiService.addClaps(66,historia.getId(),"ANUMA",3);
            callSection.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
    }

    public void terminar(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
