package co.edu.unab.hernandez.lisseth.desayunos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import co.edu.unab.hernandez.lisseth.desayunos.auth.LoginActivity;
import co.edu.unab.hernandez.lisseth.desayunos.auth.RegisterActivity;
import co.edu.unab.hernandez.lisseth.desayunos.pagesAdmin.HomeActivity;
import co.edu.unab.hernandez.lisseth.desayunos.pagesConsumer.ListaProductos;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btn_registro, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        findViewById(R.id.btn_registro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ListaProductos.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }


}
