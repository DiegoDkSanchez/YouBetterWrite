package sv.dk.com.youbetterwrite;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import sv.dk.com.youbetterwrite.Modelos.SectionsItem;
import sv.dk.com.youbetterwrite.Modelos.Story;

public class AgregarSeccion extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE;

    private Story historia;
    private SectionsItem seccion;
    private ImageView uploadImage;
    private EditText txtName, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_seccion);

        txtName = findViewById(R.id.txtSubTitulo);
        txtDescription = findViewById(R.id.txtContenido);
        uploadImage = findViewById(R.id.uploadImage);
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

    public void loadImage(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                uploadImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AgregarSeccion.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(AgregarSeccion.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}
