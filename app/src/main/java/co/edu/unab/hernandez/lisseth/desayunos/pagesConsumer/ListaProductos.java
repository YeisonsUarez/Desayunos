package co.edu.unab.hernandez.lisseth.desayunos.pagesConsumer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.adapters.AdaptadorDesayunos;
import co.edu.unab.hernandez.lisseth.desayunos.adapters.AdaptadorProductos;
import co.edu.unab.hernandez.lisseth.desayunos.models.Producto;

public class ListaProductos extends AppCompatActivity {
    RecyclerView lista_productos;
    ArrayList<Producto> productos;
    private FirebaseFirestore db ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        setTitle("Desayunos");
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        db = FirebaseFirestore.getInstance();
        lista_productos= findViewById(R.id.lista_productos);
        lista_productos.setLayoutManager(new LinearLayoutManager(this));
        getDataDB();


    }

    private void  getDataDB() {
        productos=new ArrayList<Producto>();
        productos.add(new Producto("1","https://i.pinimg.com/originals/26/05/5b/26055bd8964740f7dc2e520a5bcd3c73.jpg","Desayuno Lisseth","El desayuno contiene: buena fuente de proteina, potatsio, vitamina C, vitamina D y una serie de cosa que le aiudan mucho","$100000","Desayunos la 21"));
        productos.add(new Producto("1","https://i.pinimg.com/564x/69/51/9f/69519f4bbc2c367ca8b86fc52db8effc.jpg","Desayuno La BISBI","EL desayuno no tiene chontaduro, no sé que es eso pero que nombre tan raro xd, en fín es un buen desayuno.","$100000","DesayunaYA"));

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
                        productos.add(p);
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
        productos.add(prod1);
        productos.add(prod2);
        AdaptadorDesayunos adaptadorProductos=new AdaptadorDesayunos(productos, new AdaptadorDesayunos.onItemClickListener() {
            @Override
            public void onItemClick(Producto p, int posicion) {
                Toast.makeText(ListaProductos.this, p.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
        lista_productos.setAdapter(adaptadorProductos);
        adaptadorProductos.notifyDataSetChanged();

    }

}
