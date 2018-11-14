package sv.dk.com.youbetterwrite;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    CircleImageView profile_image;
    TextView name;
    ImageView buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        profile_image = findViewById(R.id.profile_image);
        name = findViewById(R.id.perfilNombre);
        buttonLogOut = findViewById(R.id.buttonLogOut);

        Profile profile = Profile.getCurrentProfile();
        if(profile != null){
            name.setText(profile.getName());
            Glide.with(profile_image.getContext()).load(profile.getProfilePictureUri(200,200)).into(profile_image);
        }else {
            buttonLogOut.setVisibility(View.INVISIBLE);
        }

    }

    public void agregarhistoria(View view) {
        Intent intent = new Intent(Perfil.this, AgregarHistoria.class);
        startActivity(intent);
    }

    public void navegacionInicio(View view) {
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void navegacionBuscar(View view) {
        Intent intent = new Intent(Perfil.this, Seach.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    public void navegacionFavoritos(View view) {
        Intent intent = new Intent(Perfil.this, Favoritos.class);
        startActivity(intent);
        finish();
    }

    public void navegacionPerfil(View view){

    }


    public void LogOut(View view) {
        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Perfil.this);
        builder1.setMessage("¿Estas Seguro que deseas cerrar sesion?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        LoginManager.getInstance().logOut();
                        Intent login = new Intent(Perfil.this, LoginPage.class);
                        startActivity(login);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
