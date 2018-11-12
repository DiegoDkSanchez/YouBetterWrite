package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Adapters.AddSeccionAdapter;
import sv.dk.com.youbetterwrite.Adapters.HistoriasAdapter;
import sv.dk.com.youbetterwrite.Modelos.Category;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class AgregarHistoria extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE;
    MaterialBetterSpinner spinner;
    List<String> categorias = new ArrayList<>();
    private List<Category> listaCategories;
    private String selectCategory;
    private ListView listView;
    private EditText title;
    private static AddSeccionAdapter adapter;
    private ImageView uploadPortada;
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
        uploadPortada = findViewById(R.id.uploadPortada);

        sections = new ArrayList<>();
        Story tmp = (Story) getIntent().getSerializableExtra("historia");
        if(tmp != null){
            historia = tmp;
            sections = tmp.getSections();
            title.setText(historia.getName());
            spinner.setSelection(0);
            if(historia.getUrl()!=null){
                Glide.with(uploadPortada.getContext()).load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/images/"+historia.getUrl()).into(uploadPortada);
            }

        }else {
            sections = new ArrayList<>();
            historia = new Story();
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServicioHistorias.base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ServicioHistorias apiService = retrofit.create(ServicioHistorias.class);
        Call<ResponseData> call = apiService.getHistorias();

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

    public void LoadImagePortada(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }
/*
    private void CallBackAll() {
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if(response.body() != null){
                    listaCategories = response.body().getData();
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
    }*/

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                uploadPortada.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AgregarHistoria.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(AgregarHistoria.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}