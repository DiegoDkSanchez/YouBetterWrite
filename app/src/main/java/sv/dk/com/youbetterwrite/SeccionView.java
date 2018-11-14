package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sv.dk.com.youbetterwrite.Modelos.Historia;
import sv.dk.com.youbetterwrite.Modelos.Seccion;
import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.Modelos.Story;
import sv.dk.com.youbetterwrite.Speaker.SpeakRequest;

public class SeccionView extends AppCompatActivity {

    ImageView imagen;
    TextView contenido, titulo, subtitulo, numPaginas;
    Button atras, adelante;
    CardView cardView;

    private int pagina;
    private SpeakRequest speakRequest;
    private Story historia;
    private SectionsItem seccion;
    private boolean NotIsFinalPage = true;
    private List<SectionsItem> secciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_view);

        //FindViewById
        imagen = (ImageView) findViewById(R.id.imagenHistoria);
        contenido = (TextView) findViewById(R.id.txtContenidoHistoria);
        atras = (Button) findViewById(R.id.bottomAtras);
        adelante = (Button) findViewById(R.id.bottomAdelante);
        titulo = (TextView) findViewById(R.id.txtTituloSeccion);
        subtitulo = (TextView) findViewById(R.id.txtSubtituloSeccion);
        numPaginas = (TextView) findViewById(R.id.txtNumPagina);
        cardView = findViewById(R.id.seccionCardView);

        pagina = (int) getIntent().getSerializableExtra("pagina");
        historia = (Story) getIntent().getSerializableExtra("historia");
        secciones = historia.getSections();
        seccion = secciones.get(pagina);

        if(seccion.getUrl() != null){
            Glide.with(this).load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/images/"+seccion.getUrl()).into(imagen);
        }else{
            Glide.with(this).load("http://ec2-54-244-63-119.us-west-2.compute.amazonaws.com/betterwrite/public/images/"+historia.getUrl()).into(imagen);
        }

        if(speakRequest == null) {
            speakRequest = new SpeakRequest(this);
        }

        if(pagina==historia.getSections().size()-1){
            adelante.setVisibility(View.INVISIBLE);
            NotIsFinalPage = false;
        }

        titulo.setText(historia.getName());
        if(seccion.getName()!=null){
            subtitulo.setText(seccion.getName());
        }else {
            subtitulo.setVisibility(View.INVISIBLE);
        }
        int paginaReal = pagina+1;
        numPaginas.setText("Pagina "+paginaReal);
        contenido.setText(seccion.getDescription());

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pagina != 0) {
                    Intent intent = new Intent(SeccionView.this, SeccionView.class);
                    intent.putExtra("historia", historia);
                    pagina -= 1;
                    intent.putExtra("pagina", pagina);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    speakRequest.stopSpeak();
                    finish();
                }else {
                    Intent intent = new Intent(SeccionView.this, HistoriaDetalle.class);
                    intent.putExtra("historia", historia);
                    startActivity(intent);
                    finish();
                }
            }
        });

        adelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeccionView.this, SeccionView.class);
                intent.putExtra("historia", historia);
                pagina += 1;
                intent.putExtra("pagina", pagina);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                speakRequest.stopSpeak();
                startActivity(intent);
                finish();
            }
        });

        cardView.setOnTouchListener(new OnSwipeTouchListener(SeccionView.this) {
            public void onSwipeTop() {
                //  Toast.makeText(SeccionView.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                if(pagina != 0) {
                    Intent intent = new Intent(SeccionView.this, SeccionView.class);
                    intent.putExtra("historia", historia);
                    pagina -= 1;
                    intent.putExtra("pagina", pagina);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    if(speakRequest.isSpeaking()){
                        speakRequest.stopSpeak();
                    }
                    finish();
                }else {
                    Intent intent = new Intent(SeccionView.this, HistoriaDetalle.class);
                    intent.putExtra("historia", historia);
                    startActivity(intent);
                    finish();
                }
            }
            public void onSwipeLeft() {
                if(NotIsFinalPage){
                    Intent intent = new Intent(SeccionView.this, SeccionView.class);
                    intent.putExtra("historia", historia);
                    pagina += 1;
                    intent.putExtra("pagina", pagina);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    speakRequest.stopSpeak();
                    startActivity(intent);
                    finish();
                    if(speakRequest.isSpeaking()){
                        speakRequest.stopSpeak();
                    }
                }
            }
            public void onSwipeBottom() {
                // Toast.makeText(SeccionView.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });



    }

    public void escucharSeccion(View view) {
        if(speakRequest.isSpeaking()){
            speakRequest.stopSpeak();
        }
        String texto = seccion.getName()+". \n"+seccion.getDescription();
        speakRequest.speak(texto);
    }



}