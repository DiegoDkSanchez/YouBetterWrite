package sv.dk.com.youbetterwrite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class AgregarHistoria extends AppCompatActivity {

    MaterialBetterSpinner spinner;
    List<String> categorias = new ArrayList<>();
    private String selectCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_historia);

        spinner = findViewById(R.id.selectCategoria);

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
    }
}
