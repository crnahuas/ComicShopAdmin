package com.crnahuas.comicshopapp.comics;

//Clase que representa un cómic en la tienda con sus atributos principales.
public class Comics {
    private String codigo;
    private String titulo;
    private String autor;
    private double precio;

    // Constructor vacio.
    public Comics() {
    }
    
    // Constructor que inicializa todos los campos del cómic.
    public Comics(String codigo, String titulo, String autor, double precio) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }

     // Métodos getter.
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    // Métodos setter.
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    // Representación legible del cómic para mostrar o exportar.
    @Override
    public String toString() {
        return "[" + codigo + "] " + titulo + " - " + autor + " ($" + precio + ")";
    }
 
}
