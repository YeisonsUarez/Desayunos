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
import java.util.List;


import co.edu.unab.hernandez.lisseth.desayunos.R;
import co.edu.unab.hernandez.lisseth.desayunos.models.Producto;
import co.edu.unab.hernandez.lisseth.desayunos.models.UsuarioEmpresa;

public class AdaptadorProductos extends RecyclerView.Adapter{
    ArrayList<Producto> productos;

    OnClickListener onClickListener;

    public AdaptadorProductos(ArrayList<Producto> listaProductos,  OnClickListener onClickListener) {
        this.productos = listaProductos;
        this.onClickListener = onClickListener;
    }



    class ViewHolderProducto extends RecyclerView.ViewHolder{

        TextView tvNombreEmpresa, tvNombreProducto, tvPrecio;
        ImageView iv_item_imagen_producto;

        public ViewHolderProducto(@NonNull View itemView) {
            super(itemView);
            iv_item_imagen_producto= itemView.findViewById(R.id.iv_item_imagen_producto);
            tvNombreEmpresa = itemView.findViewById(R.id.tv_item_nombre_empresa);
            tvNombreProducto = itemView.findViewById(R.id.tv_item_nombre_producto);
            tvPrecio = itemView.findViewById(R.id.tv_item_precio_producto);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_empresa,parent,false);
        return new ViewHolderProducto(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolderProducto viewHolderProducto = (ViewHolderProducto) holder;
        final Producto producto = productos.get(position);
        viewHolderProducto.tvNombreEmpresa.setText(producto.getIdEmpresa());
        viewHolderProducto.tvNombreProducto.setText(producto.getNombre());
        viewHolderProducto.tvPrecio.setText(producto.getValor());
        Glide.with(holder.itemView.getContext()).load(producto.getUrlImage()).into(viewHolderProducto.iv_item_imagen_producto);
        viewHolderProducto.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onItem(producto,position);
            }
        });

    }

    public interface OnClickListener{
        void onItem(Producto producto, int position);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
