package co.edu.unab.hernandez.lisseth.desayunos.models;

import java.io.Serializable;

public class UsuarioEmpresa implements Serializable {
    private  String idUsuario, nombre, urlFoto, descripcion,telefono, correo, contrasena, direccion, latitud, longitud;

    public UsuarioEmpresa() {
    }

    public UsuarioEmpresa(String idUsuario, String nombre, String urlFoto, String descripcion, String telefono, String correo, String contrasena, String direccion, String latitud, String longitud) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.correo = correo;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
