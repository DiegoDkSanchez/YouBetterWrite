package sv.dk.com.youbetterwrite;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sv.dk.com.youbetterwrite.Adapters.AddSeccionAdapter;
import sv.dk.com.youbetterwrite.Adapters.HistoriasAdapter;
import sv.dk.com.youbetterwrite.Modelos.Category;
import sv.dk.com.youbetterwrite.Modelos.ResponseCategory;
import sv.dk.com.youbetterwrite.Modelos.ResponseData;
import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.Modelos.Story;

import static java.security.AccessController.getContext;

public class AgregarHistoria extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE;
    MaterialSpinner spinner;
    List<String> categorias = new ArrayList<>();
    private List<Category> listaCategories;
    private Category selectCategory;
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
    Call<ResponseCategory> call = apiService.getCategories();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_historia);

        spinner = findViewById(R.id.selectCategoria);
        listView = findViewById(R.id.listSeccions);
        title = findViewById(R.id.txtTitle);
        uploadPortada = findViewById(R.id.uploadPortada);

        CallBackAll();

        sections = new ArrayList<>();
        Story tmp = (Story) getIntent().getSerializableExtra("historia");
        if(tmp != null){
            historia = tmp;
            sections = tmp.getSections();
            title.setText(historia.getName());
            if(historia.getUrl()!=null){
                Glide.with(uploadPortada.getContext())
                        .load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/images/"
                                +historia.getUrl()).into(uploadPortada);
            }
            if(historia.getUrlBitmap() != null){
                Bitmap selectedImage = loadBitmap(historia.getUrlBitmap());
                uploadPortada.setImageBitmap(selectedImage);
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
        Call<ResponseCategory> call = apiService.getCategories();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                for(Category category : listaCategories){
                    if(category.getName() == item){
                        selectCategory = category;
                    }
                }
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

            File file = new File(getPath(Uri.parse(historia.getUrlBitmap())));
            RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), title.getText().toString());
            RequestBody state = RequestBody.create(MediaType.parse("text/plain"), 3+"");
            RequestBody id_category = RequestBody.create(MediaType.parse("text/plain"), selectCategory.getId()+"");

            Call<ResponseBody> call = apiService.addStory(fbody,name,state,id_category);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        /*
        Call<ResponseBody> call = apiService.addHistoria(title.getText().toString(),3,selectCategory.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(),"Se guardo tu historia", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/

    }


    public void LoadImagePortada(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }

    private void CallBackAll() {
        call.enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {
                if(response.body() != null){
                    listaCategories = response.body().getData();
                    for(Category category : listaCategories){
                        categorias.add(category.getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable t) {
                Log.d("UDBLOG:Error",t.getMessage());
                Toast desconectado = Toast.makeText(AgregarHistoria.this, "Desconectado", Toast.LENGTH_LONG);
                desconectado.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                uploadPortada.setImageBitmap(selectedImage);
                historia.setUrlBitmap(imageUri.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AgregarHistoria.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(AgregarHistoria.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }


    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }
}