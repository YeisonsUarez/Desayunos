package co.edu.unab.hernandez.lisseth.desayunos.pagesConsumer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.adapters.AdaptadorDesayunos;
import co.edu.unab.hernandez.lisseth.desayunos.models.Producto;

public class ListaProductos extends AppCompatActivity {
    RecyclerView lista_productos;
    ArrayList<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);
        setTitle("Desayunos");
        lista_productos= findViewById(R.id.lista_productos);
        lista_productos.setLayoutManager(new LinearLayoutManager(this));
        cargarDatos();
        lista_productos.setAdapter(new AdaptadorDesayunos(productos, new AdaptadorDesayunos.onItemClickListener() {
            @Override
            public void onItemClick(Producto p, int posicion) {
                Toast.makeText(ListaProductos.this, p.getNombre(), Toast.LENGTH_SHORT).show();
            }
        }));

    }

    private void cargarDatos() {
        productos= new ArrayList<>();
        productos.add(new Producto("1","https://i.pinimg.com/originals/26/05/5b/26055bd8964740f7dc2e520a5bcd3c73.jpg","Desayuno Lisseth","El desayuno contiene: buena fuente de proteina, potatsio, vitamina C, vitamina D y una serie de cosa que le aiudan mucho","$100000","Desayunos la 21"));
        productos.add(new Producto("1","https://i.pinimg.com/564x/69/51/9f/69519f4bbc2c367ca8b86fc52db8effc.jpg","Desayuno La BISBI","EL desayuno no tiene chontaduro, no sé que es eso pero que nombre tan raro xd, en fín es un buen desayuno.","$100000","DesayunaYA"));

    }
}
