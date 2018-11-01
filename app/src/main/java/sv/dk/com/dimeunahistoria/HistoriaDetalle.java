package sv.dk.com.dimeunahistoria;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sv.dk.com.dimeunahistoria.Modelos.Historia;
import sv.dk.com.dimeunahistoria.Modelos.Seccion;

public class HistoriaDetalle extends AppCompatActivity {

    Historia historia;
    ImageView portada;
    TextView titulo, categoria, autor;
    LinearLayout fondoDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_detalle);

        //findViewById
        portada = (ImageView) findViewById(R.id.portadaDetalle);
        titulo = (TextView) findViewById(R.id.txtTituloDetalle);
        categoria = (TextView) findViewById(R.id.txtCategoriaDetalle);
        autor = (TextView) findViewById(R.id.txtAutorDetalle);
        fondoDetalle = (LinearLayout) findViewById(R.id.fondoDetalle);

        historia = (Historia)getIntent().getSerializableExtra("historia");

        Glide.with(this).load(historia.getPortada()).into(portada);
        titulo.setText(historia.getTitulo());
        categoria.setText(historia.getCategoria());
        autor.setText(historia.getAutor());

    }

    public void leerAhora(View view) {
        Intent intent = new Intent(this, SeccionView.class);
        intent.putExtra("historia", historia);
        intent.putExtra("lista", historia.getSecciones());
        intent.putExtra("pagina", 0);
        startActivity(intent);
        finish();
    }
}
