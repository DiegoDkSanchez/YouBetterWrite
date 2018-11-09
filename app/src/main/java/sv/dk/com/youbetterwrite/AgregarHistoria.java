package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Adapters.AddSeccionAdapter;
import sv.dk.com.youbetterwrite.Modelos.Category;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class AgregarHistoria extends AppCompatActivity {

    MaterialBetterSpinner spinner;
    List<String> categorias = new ArrayList<>();
    private String selectCategory;
    private ListView listView;
    private EditText title;
    private static AddSeccionAdapter adapter;

    private Story historia;
    private ArrayList<SectionsItem> sections;

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
        setContentView(R.layout.activity_agregar_historia);

        spinner = findViewById(R.id.selectCategoria);
        listView = findViewById(R.id.listSeccions);
        title = findViewById(R.id.txtTitle);

        sections = new ArrayList<>();
        Story tmp = (Story) getIntent().getSerializableExtra("historia");
        if(tmp != null){
            historia = tmp;
            sections = tmp.getSections();
            title.setText(historia.getName());
            spinner.setSelection(0);
        }else {
            sections = new ArrayList<>();
            historia = new Story();
        }



        categorias.add("Terror");
        categorias.add("Drama");
        categorias.add("Suspenso");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectCategory = categorias.get(position);
                Log.d("UDB",selectCategory+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adapter = new AddSeccionAdapter(sections, getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    public void agregarSeccion(View view) {
        historia.setName(title.getText().toString());
        historia.setSections(sections);
        //historia.setCategory();
        Intent intent = new Intent(this, AgregarSeccion.class);
        intent.putExtra("historia", historia);
        startActivity(intent);
    }

    public void subirHistoria(View view) {
/*
        Call<ResponseData> call = apiService.addConcepto(txtName.getText().toString(),Double.parseDouble(txtValue.getText().toString()),2);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
*/
    }
}