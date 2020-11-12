package co.edu.unab.hernandez.lisseth.desayunos.pagesAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpresa;

public class HomeActivity extends AppCompatActivity {
    private UsuarioEmpresa user;
    private TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user= (UsuarioEmpresa) getIntent().getSerializableExtra("user");
        info= findViewById(R.id.userInfo);
        info.setText("Nombre"+user.getNombre()+"Dir"+user.getDireccion()+"TEl"+user.getTelefono()+"correp:"+user.getCorreo()+"Descripcion"+user.getDescripcion()+"ID"+user.getIdUsuario());
    }
}
