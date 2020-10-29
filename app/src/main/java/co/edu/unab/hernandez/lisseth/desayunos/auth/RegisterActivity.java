package co.edu.unab.hernandez.lisseth.desayunos.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import co.edu.unab.hernandez.lisseth.desayunos.MainActivity;
import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpres;

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_nombre, edit_correo,edit_telefono,edit_direccion,edit_descripcion,edit_password;
    private ImageButton btn_foto, btn_atras;
    private Button btn_registrar;
    private UsuarioEmpres user;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db ;
    FirebaseUser userFirebase;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        db = FirebaseFirestore.getInstance();
        user= new UsuarioEmpres();
        edit_nombre= findViewById(R.id.edit_nombre);
        edit_correo=findViewById(R.id.edit_email);
        edit_telefono= findViewById(R.id.edit_telefono);
        edit_descripcion= findViewById(R.id.edit_descripcion);
        edit_direccion= findViewById(R.id.edit_direccion);
        edit_password= findViewById(R.id.edit_password);
        btn_atras= findViewById(R.id.btn_atras);
        btn_foto= findViewById(R.id.btn_select_photo);
        btn_registrar = findViewById(R.id.btn_registrar);
        edit_password.addTextChangedListener(loginTextWatcher);
        edit_nombre.addTextChangedListener(loginTextWatcher);
        edit_correo.addTextChangedListener(loginTextWatcher);
        edit_telefono.addTextChangedListener(loginTextWatcher);
        edit_descripcion.addTextChangedListener(loginTextWatcher);
        edit_direccion.addTextChangedListener(loginTextWatcher);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

            }
        });

    }
    public TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            user.setNombre(edit_nombre.getText().toString());
            user.setCorreo(edit_correo.getText().toString());
            user.setDescripcion(edit_descripcion.getText().toString());
            user.setDireccion(edit_direccion.getText().toString());
            user.setTelefono(edit_telefono.getText().toString());
            user.setContrasena(edit_password.getText().toString());
            btn_registrar.setEnabled(!user.getCorreo().isEmpty()&&!user.getNombre().isEmpty()&&!user.getContrasena().isEmpty()&&!user.getDireccion().isEmpty()&&!user.getTelefono().isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public void createUser(){

        mAuth.createUserWithEmailAndPassword(user.getCorreo(), user.getContrasena())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Register", "createUserWithEmail:success");
                            userFirebase = mAuth.getCurrentUser();
                            user.setIduser(userFirebase.getUid());
                            userFirebase.sendEmailVerification();

                            Toast.makeText(RegisterActivity.this, saveDataUser()?"Datos de usuario almacenados":"Fallo al intentar guardar datos de Usuario.",
                                    Toast.LENGTH_SHORT).show();
                            edit_correo.setText("");
                            edit_nombre.setText("");
                            edit_password.setText("");
                            edit_direccion.setText("");
                            edit_descripcion.setText("");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Fail register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Error al intentar registrar usuario, intente de nuevo.",
                                    Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }

    boolean saveDataUser(){
        db.collection("Usuarios").document(userFirebase.getUid())
                .set(user, SetOptions.merge());
        return true;
    }
}
