package com.crnahuas.comicshopapp.users;

import com.crnahuas.comicshopapp.comics.Comics;
import java.util.ArrayList;

// Clase que representa un Usuario con RUT, nombre y lista de cómics comprados.
public class Usuarios {

    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private ArrayList<Comics> compras;

    // Constructor vacio.
    public Usuarios() {
    }

    // Constructor que inicializa el usuario y su lista de compras.
    public Usuarios(String rut, String nombre, String apellidoPaterno, String apellidoMaterno) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.compras = new ArrayList<>();
    }

    // Métodos getter.
    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public ArrayList<Comics> getCompras() {
        return compras;
    }

    // Métodos setter.
    public void setRut(String rut) {
        this.rut = rut;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public void setCompras(ArrayList<Comics> compras) {
        this.compras = compras;
    }

    // Agrega un cómic a la lista de compras del usuario.
    public void agregarCompra(Comics comic) {
        if (comic != null) {
            compras.add(comic);
        }
    }

    // Representación usuario para impresión o exportación.
    @Override
    public String toString() {
        return String.format("RUT: %s, Nombre: %s %s %s, Total de cómics comprados: %d",
                rut, nombre, apellidoPaterno, apellidoMaterno, compras.size());
    }
}
