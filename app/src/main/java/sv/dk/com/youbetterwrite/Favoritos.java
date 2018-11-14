package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Favoritos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
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
}
