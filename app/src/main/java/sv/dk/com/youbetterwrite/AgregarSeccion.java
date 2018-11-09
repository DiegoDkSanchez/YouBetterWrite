package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class AgregarSeccion extends AppCompatActivity {

    private Story historia;
    private SectionsItem seccion;

    private EditText txtName, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_seccion);

        txtName = findViewById(R.id.txtSubTitulo);
        txtDescription = findViewById(R.id.txtContenido);

        historia = (Story) getIntent().getSerializableExtra("historia");


    }

    public void agregarSeccionEnHistoria(View view) {
        SectionsItem section = new SectionsItem();
        section.setName(txtName.getText().toString());
        section.setDescription(txtDescription.getText().toString());
        ArrayList<SectionsItem> tmp = historia.getSections();
        tmp.add(section);
        historia.setSections(tmp);

        Intent intent = new Intent(this, AgregarHistoria.class);
        intent.putExtra("historia", historia);
        startActivity(intent);
        finish();
    }
}
