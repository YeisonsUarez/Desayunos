package co.edu.unab.hernandez.lisseth.desayunos.auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

import co.edu.unab.hernandez.lisseth.desayunos.MainActivity;
import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpresa;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_GALERY_CAPTURE = 0;
    private EditText etNombre, etCorreo, etTelefono, etDireccion, etDescripcion, etContrasena;
    private ImageButton btnFoto, btnAtras;
    private Button btnRegistrar;
    private UsuarioEmpresa usuarioEmpresa;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db ;
    FirebaseUser userFirebase;
    boolean estaRegistrado;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        db = FirebaseFirestore.getInstance();
        usuarioEmpresa = new UsuarioEmpresa();
        etNombre = findViewById(R.id.et_nombre_registro);
        etCorreo =findViewById(R.id.et_correo_registro);
        etTelefono = findViewById(R.id.et_telefono_registro);
        etDescripcion = findViewById(R.id.et_descripcion_registro);
        etDireccion = findViewById(R.id.et_direccion_registro);
        etContrasena = findViewById(R.id.et_pass_registro);
        btnAtras = findViewById(R.id.btn_atras_registro);
        btnFoto = findViewById(R.id.btn_select_photo_registro);
        btnRegistrar = findViewById(R.id.btn_registrar);
        etContrasena.addTextChangedListener(loginTextWatcher);
        etNombre.addTextChangedListener(loginTextWatcher);
        etCorreo.addTextChangedListener(loginTextWatcher);
        etTelefono.addTextChangedListener(loginTextWatcher);
        etDescripcion.addTextChangedListener(loginTextWatcher);
        etDireccion.addTextChangedListener(loginTextWatcher);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(RegisterActivity.this);
            }
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Tomar foto", "Seleccionar de galeria","Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Añade una foto de perfil");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Tomar foto")) {
                    dispatchTakePictureIntent();

                } else if (options[item].equals("Seleccionar de galeria")) {
                    dispatchChooseFileIntent();

                } else if (options[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void dispatchChooseFileIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_GALERY_CAPTURE
        );
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }


    public TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            usuarioEmpresa.setNombre(etNombre.getText().toString());
            usuarioEmpresa.setCorreo(etCorreo.getText().toString());
            usuarioEmpresa.setDescripcion(etDescripcion.getText().toString());
            usuarioEmpresa.setDireccion(etDireccion.getText().toString());
            usuarioEmpresa.setTelefono(etTelefono.getText().toString());
            usuarioEmpresa.setContrasena(etContrasena.getText().toString());
            if(!usuarioEmpresa.getCorreo().isEmpty()&&!usuarioEmpresa.getNombre().isEmpty()&&!usuarioEmpresa.getContrasena().isEmpty()&&!usuarioEmpresa.getDireccion().isEmpty()&&!usuarioEmpresa.getTelefono().isEmpty()){
                btnRegistrar.setEnabled(true);
                btnRegistrar.setTextColor(getColor(R.color.btn_enabled));
            }else{
                btnRegistrar.setEnabled(false);
                btnRegistrar.setTextColor(getColor(R.color.btn_disabled));}

        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void createUser(){

        mAuth.createUserWithEmailAndPassword(usuarioEmpresa.getCorreo(), usuarioEmpresa.getContrasena())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Registrar", "Crear Usuario con correo: Aprobado");
                            userFirebase = mAuth.getCurrentUser();
                            usuarioEmpresa.setIdUsuario(userFirebase.getUid());
                            userFirebase.sendEmailVerification();

                            Toast.makeText(RegisterActivity.this, saveDataUser()?"Datos de usuario almacenados":"Fallo al intentar guardar datos de Usuario.",
                                    Toast.LENGTH_SHORT).show();
                            etCorreo.setText("");
                            etNombre.setText("");
                            etContrasena.setText("");
                            etDireccion.setText("");
                            etDescripcion.setText("");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Registro Fallido", "Crear Usuario con correo: Denegado", task.getException());
                            Toast.makeText(RegisterActivity.this, "Error al intentar registrar usuario, intente de nuevo.",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    boolean saveDataUser(){
        StorageReference storageReference =  FirebaseStorage.getInstance().getReference();
        StorageReference ref= storageReference.child("images/"+ UUID.randomUUID().toString());
        ref.putFile(imageUri)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(
                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                usuarioEmpresa.setUrlFoto(taskSnapshot.getStorage().getDownloadUrl().getResult().toString());
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {


                        Toast
                                .makeText(RegisterActivity.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onProgress(
                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());

                            }
                        });
        db.collection("Usuarios").document(userFirebase.getUid())
                .set(usuarioEmpresa, SetOptions.merge());
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            try{
            btnFoto.setImageBitmap(imageBitmap);
            imageUri=data.getData();}
            catch (Exception e){

            }

        }else{
            if(requestCode== REQUEST_GALERY_CAPTURE && resultCode==RESULT_OK) {
                try {
                    imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    try{btnFoto.setImageBitmap(selectedImage);}catch (Exception e){}
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
