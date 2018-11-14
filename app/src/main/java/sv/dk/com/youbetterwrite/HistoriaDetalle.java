package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import sv.dk.com.youbetterwrite.Modelos.Historia;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class HistoriaDetalle extends AppCompatActivity {

    Story historia;
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

        historia = (Story) getIntent().getSerializableExtra("historia");

        Glide.with(this).load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/images/"+historia.getUrl()).into(portada);
        titulo.setText(historia.getName());
        categoria.setText(historia.getCategory().getName());
        autor.setText(historia.getAutor().getFirst_name() + " " + historia.getAutor().getLast_name());

    }

    public void leerAhora(View view) {
        Intent intent = new Intent(this, SeccionView.class);
        intent.putExtra("historia", historia);
        //intent.putExtra("lista", historia.getSections());
        intent.putExtra("pagina", 0);
        if(historia.getSections() != null && historia.getSections().size() != 0){
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"Historia sin secciones", Toast.LENGTH_SHORT).show();
        }

    }
}