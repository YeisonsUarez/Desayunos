package co.edu.unab.hernandez.lisseth.desayunos;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.unab.hernandez.lisseth.desayunos.adapters.AdaptadorProductos;
import co.edu.unab.hernandez.lisseth.desayunos.auth.LoginActivity;
import co.edu.unab.hernandez.lisseth.desayunos.auth.RegisterActivity;
import co.edu.unab.hernandez.lisseth.desayunos.models.Producto;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpresa;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductosFragment extends Fragment {

    ArrayList<Producto> misProductos;
    RecyclerView rvProductos;
    RecyclerView.LayoutManager layoutManager;
    Button btnAgregar, btnSalir;
    private FirebaseFirestore db ;
    private FirebaseAuth mAuth;
    ImageButton iv_dialogo;

    FirebaseUser userFirebase;

    UsuarioEmpresa user;


    public ProductosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        misProductos= new ArrayList<Producto>();
        db = FirebaseFirestore.getInstance();
        user = (UsuarioEmpresa) getArguments().getSerializable("usuario");
        userFirebase = mAuth.getCurrentUser();
        btnAgregar = view.findViewById(R.id.btn_agregar_productos_home);
        btnSalir = view.findViewById(R.id.btn_salir_productos_home);
        rvProductos = view.findViewById(R.id.rv_productos);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        rvProductos.setLayoutManager(layoutManager);
        getDataDB();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarDialogo(null);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });





        return view;
    }

    private void  getDataDB() {
        misProductos=new ArrayList<Producto>();
        db.collection("Desayunos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document: task.getResult()
                         ) {
                        Producto p =new Producto();
                        p.setIdEmpresa(document.getString("idEmpresa"));
                        p.setNombre(document.getString("nombre"));
                        p.setDetalle(document.getString("detalle"));
                        p.setValor(document.getString("valor"));
                        p.setIdProducto(document.getString("idProducto"));
                        p.setUrlImage("https://okdiario.com/img/2018/07/03/receta-de-desayuno-americano-1.jpg");
                        misProductos.add(p);
                    }
                }
            }

        });



        Producto prod1 = new Producto();
        prod1.setIdProducto("Desayunos Yaaa");
        prod1.setUrlImage("");
        prod1.setNombre("Desayuno Mágico");
        prod1.setDetalle("Usted podrá elegir los productos");
        prod1.setValor("42.000");
        prod1.setIdEmpresa("Desayunos Yaaa");

        Producto prod2 = new Producto();
        prod2.setIdProducto("Desayunos Saludables");
        prod2.setUrlImage("");
        prod2.setNombre("Desayuno de frutas");
        prod2.setDetalle("Usted podrá elegir los productos");
        prod2.setValor("42.000");
        prod2.setIdEmpresa("Desayunos Saludables");
        misProductos.add(prod1);
        misProductos.add(prod2);
        AdaptadorProductos adaptadorProductos = new AdaptadorProductos(misProductos, new AdaptadorProductos.OnClickListener() {
            @Override
            public void onItem(Producto producto, int position) {
                Toast.makeText(getActivity(), "fddsg", Toast.LENGTH_SHORT).show();

                mostrarDialogo(producto);
            }
        });
        adaptadorProductos.notifyDataSetChanged();
        rvProductos.setAdapter(adaptadorProductos);
    }

    public void mostrarDialogo(final Producto producto){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialogo_producto, null);
            builder.setView(view);

            final AlertDialog dialog = builder.create();



        final EditText etNombre, etDetalle, etValor;


        etNombre = view.findViewById(R.id.et_nombre_producto_dialogo);
        etDetalle = view.findViewById(R.id.et_detalle_producto_dialogo);
        etValor = view.findViewById(R.id.et_valor_producto_dialogo);
        iv_dialogo= view.findViewById(R.id.iv_dialogo);

        final Button btnEditarDialog = view.findViewById(R.id.btn_editar_producto_dialogo), btnCancelarDialog = view.findViewById(R.id.btn_salir_producto_dialogo);
        iv_dialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    startGallery();
                }
            }
        });
        // ir a detalle de producto
        if(producto!=null) {
            etNombre.setText(producto.getNombre());
            etDetalle.setText(producto.getDetalle());
            etValor.setText(producto.getValor());
            btnEditarDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etNombre.isEnabled()) {
                        etNombre.setEnabled(false);
                        etDetalle.setEnabled(false);
                        etValor.setEnabled(false);

                        btnEditarDialog.setText(R.string.btn_editar_perfil_home);
                        btnCancelarDialog.setText(R.string.btn_cancelar_perfil_home);
                        Producto producto1= new Producto();
                        producto1.setNombre(etNombre.getText().toString());
                        producto1.setDetalle(etDetalle.getText().toString());
                        producto1.setValor(etValor.getText().toString());
                        if(!misProductos.contains(producto)){
                            misProductos.remove(producto);
                            misProductos.add(producto1);
                        }
                        getDataDB();
                        dialog.dismiss();

                    } else {
                        etNombre.setEnabled(true);
                        etDetalle.setEnabled(true);
                        etValor.setEnabled(true);

                        btnEditarDialog.setText(R.string.btn_guardar_perfil_home);
                        btnCancelarDialog.setText(R.string.btn_cancelar_perfil_home);
                    }

                }
            });

            btnCancelarDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etNombre.setEnabled(false);
                    etDetalle.setEnabled(false);
                    etValor.setEnabled(false);

                    etNombre.setText(producto.getNombre());
                    etDetalle.setText(producto.getDetalle());
                    etValor.setText(producto.getValor());

                    dialog.dismiss();
                }
            });
            /*etNombre.setText(misProductos.get(lugar).getNombre());
            etDetalle.setText(misProductos.get(lugar).getDetalle());
            etValor.setText(misProductos.get(lugar).getValor());*/
        }else{

            etNombre.setEnabled(true);
            etDetalle.setEnabled(true);
            etValor.setEnabled(true);

            btnEditarDialog.setText(R.string.btn_guardar_perfil_home);
            btnCancelarDialog.setText(R.string.btn_cancelar_perfil_home);
            guardarDesayuno(etNombre.getText().toString(),etDetalle.getText().toString(),etValor.getText().toString());
            dialog.dismiss();


            btnCancelarDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etNombre.setEnabled(false);
                    etDetalle.setEnabled(false);
                    etValor.setEnabled(false);

                    etNombre.setText("");
                    etDetalle.setText("");
                    etValor.setText("");

                    dialog.dismiss();
                }
            });


        }
        dialog.show();
    }
    void guardarDesayuno(String nombre, String detalle, String precio){
        Producto producto= new Producto(nombre, null,nombre,detalle,precio,user.getNombre());
        db.collection("Desayunos").document(userFirebase.getUid()+nombre).set(producto,SetOptions.merge());
        getDataDB();
    }
    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if(resultCode == RESULT_OK) {
            if(requestCode == 1000){
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iv_dialogo.setImageBitmap(bitmapImage);
            }
        }
    }



}