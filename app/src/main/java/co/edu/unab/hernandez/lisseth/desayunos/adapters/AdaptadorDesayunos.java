package co.edu.unab.hernandez.lisseth.desayunos.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.models.Producto;

public class AdaptadorDesayunos extends RecyclerView.Adapter {
    ArrayList<Producto> productoArrayList;
    onItemClickListener miListener;

    public AdaptadorDesayunos(ArrayList<Producto> productoArrayList, onItemClickListener miListener) {
        this.productoArrayList = productoArrayList;
        this.miListener = miListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nombre,precio,detalle,empresa;
        public ImageView foto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            empresa= itemView.findViewById(R.id.nombre_empresa);
            foto= itemView.findViewById(R.id.item_imagen_producto);
            nombre= itemView.findViewById(R.id.item_nombre_producto);
            precio= itemView.findViewById(R.id.item_precio_producto);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto,parent,false);
        return new ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder =(ViewHolder)holder;
        final Producto producto= productoArrayList.get(position);
        viewHolder.nombre.setText(producto.getNombre());
        viewHolder.empresa.setText(producto.getIdEmpresa());
        viewHolder.precio.setText(producto.getValor());
        Glide.with(viewHolder.itemView.getContext()).load(producto.getUrlImage()).into(viewHolder.foto);

        //imagen
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miListener.onItemClick(producto,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }
    public interface onItemClickListener{
        void onItemClick(Producto p, int posicion);

    }


}
