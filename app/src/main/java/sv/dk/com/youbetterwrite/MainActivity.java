package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Adapters.HistoriasAdapter;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class MainActivity extends AppCompatActivity implements HistoriasAdapter.ItemClickListener {

    RecyclerView recyclerView;
    HistoriasAdapter adaptador;
    List<Story> listaHistorias;
    SwipeRefreshLayout swipeRefreshLayout;

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ServicioHistorias.base_url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    ServicioHistorias apiService = retrofit.create(ServicioHistorias.class);
    Call<ResponseData> call = apiService.getHistorias();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FindViewById
        recyclerView = (RecyclerView) findViewById(R.id.reciclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        listaHistorias = new ArrayList<>();

        swipeRefreshLayout.setRefreshing(true);
        CallBackAll();

        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();

        if(AccessToken.getCurrentAccessToken() == null){
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               call.cancel();
                if(call.isCanceled()){
                    apiService = retrofit.create(ServicioHistorias.class);
                    call = apiService.getHistorias();
                    Log.d("WTF","WTF");
                }
                CallBackAll();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {


        Story historia = listaHistorias.get(position);

        Intent intent = new Intent(this, HistoriaDetalle.class);
        intent.putExtra("historia", historia);
        startActivity(intent);
    }

    private void CallBackAll() {
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.body() != null){
                    listaHistorias = response.body().getData();
                }
                recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
                adaptador = new HistoriasAdapter(MainActivity.this, listaHistorias);
                recyclerView.setAdapter(adaptador);
                adaptador.setClickListener(MainActivity.this);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("UDBLOG:Error",t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
                Toast desconectado = Toast.makeText(MainActivity.this, "Desconectado", Toast.LENGTH_LONG);
                desconectado.show();
            }
        });
    }


    public void navegacionInicio(View view) {

    }

    public void navegacionBuscar(View view) {
        Intent intent = new Intent(MainActivity.this, Busqueda.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void navegacionFavoritos(View view) {
        Intent intent = new Intent(MainActivity.this, Favoritos.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    public void navegacionPerfil(View view){
        Intent intent = new Intent(MainActivity.this, Perfil.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
