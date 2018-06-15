package camaraapp.remachepilco.pm.facci.taerea_movil_imajen4tob.poo.pra1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import facci.pm.ta2.poo.datalevel.DataException;
import facci.pm.ta2.poo.datalevel.DataObject;
import facci.pm.ta2.poo.datalevel.SaveCallback;

public class InsertActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonCamara, buttonInsert;
    ImageView imageViewFoto;
    EditText editTextN, editTextP, editTextD;
    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        buttonCamara = (Button) findViewById(R.id.buttonCamara);
        buttonInsert = (Button) findViewById(R.id.ButtonInsert);
        imageViewFoto = (ImageView) findViewById(R.id.imageViewFoto);
        editTextD = (EditText)findViewById(R.id.TXTDesc);
        editTextN = (EditText)findViewById(R.id.TXTNombre);
        editTextP = (EditText)findViewById(R.id.TXTPrecio);
        buttonCamara.setOnClickListener(this);

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DataObject objeto = new DataObject("item");
                imageViewFoto.buildDrawingCache();
                final Bitmap bmap = imageViewFoto.getDrawingCache();
                objeto.saveInBackground(new SaveCallback<DataObject>() {

                    @Override
                    public void done(DataObject object, DataException e) {
                        object.put("price", editTextP.getText().toString());
                        object.put("name", editTextN.getText().toString());
                        object.put("description", editTextD.getText().toString());
                        object.put("image", bmap);
                        object.save();
                        Toast.makeText(getApplicationContext(),"Inserción Correcta", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(InsertActivity.this, ResultsActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Esconde el teclado cuando se da click en cualquier parte de la Activity que no sea un EditText
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri img = imageReturnedIntent.getData();
            imageViewFoto.setImageURI(img);
        }
    }
}
