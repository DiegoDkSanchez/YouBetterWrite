package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Adapters.HistoriasAdapter;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class Favoritos extends AppCompatActivity implements HistoriasAdapter.ItemClickListener{

    RecyclerView recyclerView;
    HistoriasAdapter adaptador;
    List<Story> listaHistorias;
    LinearLayout linearLayout;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ServicioHistorias.base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    ServicioHistorias apiService = retrofit.create(ServicioHistorias.class);
    Call<ResponseData> call = apiService.getBetter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        recyclerView = (RecyclerView) findViewById(R.id.reciclerViewFavoritos);
        linearLayout = findViewById(R.id.MainLayoutMainActivity);
        listaHistorias = new ArrayList<>();

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.body() != null){
                    listaHistorias = response.body().getData();
                }
                recyclerView.setLayoutManager(new GridLayoutManager(Favoritos.this, 1));
                adaptador = new HistoriasAdapter(Favoritos.this, listaHistorias);
                recyclerView.setAdapter(adaptador);
                adaptador.setClickListener(Favoritos.this);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("UDBLOG:Error",t.getMessage());
                Toast desconectado = Toast.makeText(Favoritos.this, "Desconectado", Toast.LENGTH_LONG);
                desconectado.show();
            }
        });
    }

    public void navegacionInicio(View view) {
        Intent intent = new Intent(Favoritos.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void navegacionBuscar(View view) {
        Intent intent = new Intent(Favoritos.this, Seach.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    public void navegacionFavoritos(View view) {

    }

    public void navegacionPerfil(View view){
        Intent intent = new Intent(Favoritos.this, Perfil.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(View view, int position) {
        Story historia = listaHistorias.get(position);
        Intent intent = new Intent(this, HistoriaDetalle.class);
        intent.putExtra("historia", historia);
        startActivity(intent);
    }
}
