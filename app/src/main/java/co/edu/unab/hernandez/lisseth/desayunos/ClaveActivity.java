package co.edu.unab.hernandez.lisseth.desayunos;

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
import com.google.firebase.auth.FirebaseAuth;

public class ClaveActivity extends AppCompatActivity {

    private Button enviar;
    private ImageButton atras;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clave);
        atras= findViewById(R.id.atras_contrasena);
        enviar= findViewById(R.id.enviar_contrasena);
        email= findViewById(R.id.email_contrasena);
        email.addTextChangedListener(loginTextWatcher);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ClaveActivity.this, LoginActivity.class));
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.setLanguageCode("es");
                auth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Contraseña", "Email sent.");
                                    email.setText("");
                                    Toast.makeText(ClaveActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ClaveActivity.this, "No se puede enviar el email", Toast.LENGTH_SHORT).show();
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
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            enviar.setEnabled(!email.getText().toString().isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}
