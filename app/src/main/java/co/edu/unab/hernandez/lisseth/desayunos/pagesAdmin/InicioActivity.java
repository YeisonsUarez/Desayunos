package co.edu.unab.hernandez.lisseth.desayunos.pagesAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.edu.unab.hernandez.lisseth.desayunos.PerfilFragment;
import co.edu.unab.hernandez.lisseth.desayunos.ProductosFragment;
import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpresa;

public class InicioActivity extends AppCompatActivity {
    BottomNavigationView btm_view;
    UsuarioEmpresa user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btm_view = findViewById(R.id.bottom_view);
        btm_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.perfil_fragment){
                    getSupportActionBar().setTitle(R.string.title_perfil_empresa_fragment);
                    getFragment(new PerfilFragment());
                }else if(item.getItemId() == R.id.productos_fragment){
                    getSupportActionBar().setTitle(R.string.title_productos_fragment);
                    getFragment(new ProductosFragment());
                }

                return false;
            }
        });


    }

    private void getFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if(fragment != null && fragment.isVisible()) {
            fragmentTransaction.add(R.id.container, fragment);
        }else{
            fragmentTransaction.replace(R.id.container, fragment);
        }
            user = (UsuarioEmpresa) getIntent().getSerializableExtra("usuario");
            Bundle bundle = new Bundle();
            bundle.putSerializable("usuario", user);
            fragment.setArguments(bundle);
            fragmentTransaction.commit();

    }
}
