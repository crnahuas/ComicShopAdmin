package com.crnahuas.comicshopapp.users;

import java.util.ArrayList;

// Clase que representa un Usuario con RUT, nombre y lista de cómics comprados.
public class Usuarios {

    private String rut;
    private String nombre;
    private ArrayList<Comic> compras;

     // Constructor vacio.
    public Usuarios() {
    }

    // Constructor que inicializa el usuario y su lista de compras.
    public Usuarios(String rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        this.compras = new ArrayList<>();
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }
    
    
    public ArrayList<Comic> getCompras() {
        return compras;
    }

    // Agrega un cómic a la lista de compras del usuario.
    public void agregarCompra(Comic comic) {
        if (comic != null) {
            compras.add(comic);
        }
    }
    
    // Representación del usuario para impresión o exportación.
    @Override
    public String toString() {
        return String.format("RUT: %s, Nombre: %s, Total de cómics comprados: %d",
                rut, nombre, compras.size());
    }
}
