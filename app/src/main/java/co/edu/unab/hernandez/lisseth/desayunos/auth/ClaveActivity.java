package co.edu.unab.hernandez.lisseth.desayunos.auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
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
import com.google.firebase.auth.FirebaseAuth;

import co.edu.unab.hernandez.lisseth.desayunos.R;

public class ClaveActivity extends AppCompatActivity {

    private Button btnEnviar;
    private ImageButton ibAtras;
    private EditText etCorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave);
        ibAtras = findViewById(R.id.atras_contrasena);
        btnEnviar = findViewById(R.id.btn_enviar_clave);
        etCorreo = findViewById(R.id.et_correo_clave);
        etCorreo.addTextChangedListener(loginTextWatcher);
        ibAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClaveActivity.this, LoginActivity.class));
            }
        });
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.setLanguageCode("es");
                auth.sendPasswordResetEmail(etCorreo.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Contraseña", "Correo Enviado.");
                                    etCorreo.setText("");
                                    Toast.makeText(ClaveActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ClaveActivity.this, "No se puede enviar el correo", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

    }
    public TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(!etCorreo.getText().toString().isEmpty()){
                btnEnviar.setEnabled(true);
                btnEnviar.setTextColor(getColor(R.color.btn_enabled));
            }else{
                btnEnviar.setEnabled(false);
                btnEnviar.setTextColor(getColor(R.color.btn_disabled));
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
