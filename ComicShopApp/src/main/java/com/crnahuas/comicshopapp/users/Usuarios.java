package com.crnahuas.comicshopapp.users;

import java.util.ArrayList;

public class Usuarios {

    private String rut;
    private String nombre;
    private ArrayList<Comic> compras;

    public Usuarios() {
    }

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

    public void agregarCompra(Comic comic) {
        if (comic != null) {
            compras.add(comic);
        }
    }

    @Override
    public String toString() {
        return String.format("RUT: %s, Nombre: %s, Total de cómics comprados: %d",
                rut, nombre, compras.size());
    }
}
