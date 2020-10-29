package co.edu.unab.hernandez.lisseth.desayunos.models;

import java.io.Serializable;

public class Producto implements Serializable {
    private  String idProducto, urlImage, nombre, detalle, valor, idEmpresa;

    public Producto() {
    }

    public Producto(String idProducto, String urlImage, String nombre, String detalle, String valor, String idEmpresa) {
        this.idProducto = idProducto;
        this.urlImage = urlImage;
        this.nombre = nombre;
        this.detalle = detalle;
        this.valor = valor;
        this.idEmpresa = idEmpresa;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
