package com.crnahuas.comicshopapp;

import com.crnahuas.comicshopapp.comics.Comics;
import com.crnahuas.comicshopapp.users.Usuarios;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;

// Sistema principal que administra la tienda de cómics y usuarios.
//Utiliza colecciones, manejo de archivos y validaciones para una gestión robusta.
public class ComicShopApp {
    // Almacena todos los cómics disponibles.
    static ArrayList<Comics> comicsDisponibles = new ArrayList<>();
    // Asocia RUT con los objetos Usuario.
    static HashMap<String, Usuarios> usuarios = new HashMap<>();
    // Asegura que códigos de cómics sea unico.
    static HashSet<String> codigosUnicos = new HashSet<>();
    // Ordenada RUTs para mostrar/exportar usuarios ordenadamente.
    static TreeSet<String> usuariosOrdenados = new TreeSet<>();
    // Lectura desde consola.
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
    
}
