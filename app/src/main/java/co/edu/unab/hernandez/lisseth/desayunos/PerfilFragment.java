package co.edu.unab.hernandez.lisseth.desayunos;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import co.edu.unab.hernandez.lisseth.desayunos.auth.LoginActivity;
import co.edu.unab.hernandez.lisseth.desayunos.auth.RegisterActivity;
import co.edu.unab.hernandez.lisseth.desayunos.models.Producto;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpresa;
import co.edu.unab.hernandez.lisseth.desayunos.pagesAdmin.InicioActivity;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private static final int REQUEST_GALERY_CAPTURE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private UsuarioEmpresa user;
    private EditText etNombre, etCorreo, etTelefono, etDireccion, etDescripcion;
    private Button btnEditar, btnSalir;
    private String nombre, correo, telefono, direccion, descripcion;
    private ImageView ivFoto;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        user = (UsuarioEmpresa) getArguments().getSerializable("usuario");

        etNombre = view.findViewById(R.id.et_nombre_home);
        etCorreo = view.findViewById(R.id.et_correo_home);
        etTelefono = view.findViewById(R.id.et_telefono_home);
        etDireccion = view.findViewById(R.id.et_direccion_home);
        etDescripcion = view.findViewById(R.id.et_descripcion_home);

        etNombre.setText(user.getNombre());
        etCorreo.setText(user.getCorreo());
        etTelefono.setText(user.getTelefono());
        etDireccion.setText(user.getDireccion());
        etDescripcion.setText(user.getDescripcion());

        nombre = etNombre.getText().toString();
        correo = etCorreo.getText().toString();
        telefono = etTelefono.getText().toString();
        direccion = etDireccion.getText().toString();
        descripcion = etDescripcion.getText().toString();

        btnEditar = view.findViewById(R.id.btn_editar_perfil_home);
        btnSalir = view.findViewById(R.id.btn_salir_perfil_home);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombre.isEnabled()){
                    etNombre.setEnabled(false);
                    etCorreo.setEnabled(false);
                    etTelefono.setEnabled(false);
                    etDireccion.setEnabled(false);
                    etDescripcion.setEnabled(false);

                    btnEditar.setText(R.string.btn_editar_perfil_home);
                    btnSalir.setText(R.string.btn_salir_perfil_home);

                }else{
                    etNombre.setEnabled(true);
                    etCorreo.setEnabled(true);
                    etTelefono.setEnabled(true);
                    etDireccion.setEnabled(true);
                    etDescripcion.setEnabled(true);

                    btnEditar.setText(R.string.btn_guardar_perfil_home);
                    btnSalir.setText(R.string.btn_cancelar_perfil_home);
                }
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombre.isEnabled()){
                    etNombre.setEnabled(false);
                    etCorreo.setEnabled(false);
                    etTelefono.setEnabled(false);
                    etDireccion.setEnabled(false);
                    etDescripcion.setEnabled(false);

                    etNombre.setText(nombre);
                    etCorreo.setText(correo);
                    etTelefono.setText(telefono);
                    etDireccion.setText(direccion);
                    etDescripcion.setText(descripcion);

                    btnEditar.setText(R.string.btn_editar_perfil_home);
                    btnSalir.setText(R.string.btn_salir_perfil_home);
                }else{
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
